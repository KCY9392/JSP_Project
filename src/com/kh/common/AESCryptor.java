package com.kh.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

// 양방향 암호화 지원하는 클래스
// java.api 기본적으로 제공하고 있음
// javax.crypto, java.security패키지에 구성되어있음

// 저희만의 key를 사용할 것임.
// key 가 진짜 중요함 key프로젝트에 한개만 있어야한다
public class AESCryptor {

   private static SecretKey key; //암호화를 위한 키
   private String path;// 파일로 저장된 암호화키의 위치
   
   public AESCryptor() {
       // 클래스가 인스턴스화될때 (생성될때) 기본설정을 해줘야함
       // 1. key파일이 있다면, key파일에서 SecretKey객체를 불러오고
       //            없다면, SecretKey객체를 생성하여 파일로 저장
       
       this.path = AESCryptor.class.getResource("/").getPath();
       //현재파일이 존재하는 위치를 this.path에 넣어줌
       
       this.path = this.path.substring(0, this.path.indexOf("classes"));
       
       System.out.println(path);
       
       File f = new File(this.path+"/bslove.bs");
       //파일객체 생성
       // key를 저장하고 있는 파일이름이 bslove.bs 안에 SecretKey객체가 저장되어있음
       
       if(f.exists()) {
           // key를 저장하는 파일이 있으면
           try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
               
               this.key = (SecretKey)ois.readObject();
                       //안에 파일로 저장되어있는 것을 객체로 저장하겠다
               
           }catch(IOException e){
               e.printStackTrace();
           } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       }else {
           // SecretKey객체가 없으니까 SecretKey객체 생성
           if(key==null) {
               getGenerator(); // key값을 생성하는 메소드
               
           }
       }
   }
   
   private void getGenerator() {
       // SecretKey를 생성하는 메소드
       SecureRandom ser = new SecureRandom();

       // key를 생성해주는 클래스
       KeyGenerator keygen = null;

       // 1. 적용할 알고리즘 : AES => key가 한개짜리인 알고리즘, RSA => key가 두개짜리 (공개키,개인키)
       try {
           keygen = KeyGenerator.getInstance("AES");

           // key값 생성
           keygen.init(128, ser);
           this.key = keygen.generateKey(); // SecretKey객체 반환해서 key에 저장

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }

       // 생성된 key객체를 정해진 경로에 파일로 저장해서 관리하기
       File f = new File(this.path+"/bslove.bs");
       
       try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));) {
           oos.writeObject(this.key);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }
   
   public static String encrypt(String str) {
       
       String resultValue = "";
       //key를 가지고 암호화처리하는 클래스가 있음
       //Cipher
       try {
        Cipher cipher = Cipher.getInstance("AES");
        
        cipher.init(Cipher.ENCRYPT_MODE,AESCryptor.key); //mode는 암호화인지 복호화인지 설정하고, key값도 같이 제시 
        
        //매개변수로 넘어온값 암호화처리하기
        byte[] encrypt = str.getBytes(Charset.forName("UTF-8"));
        byte[] result = cipher.doFinal(encrypt);
        resultValue = Base64.getEncoder().encodeToString(result);
        
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
        e.printStackTrace();
    } catch (InvalidKeyException e) {
        e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
        e.printStackTrace();
    } catch (BadPaddingException e) {
        e.printStackTrace();
    } 
       return resultValue;
   }
   
   
   //생성된 키를 가지고 복호화(원본값으로 변경)하는 메소드
   public static String decrypt(String encryptedstr) {
       
       String oriVal = "";
       
    try {
        Cipher cipher = Cipher.getInstance("AES");
        
        cipher.init(Cipher.DECRYPT_MODE, AESCryptor.key);
        
        byte[] decrypt = Base64.getDecoder().decode(encryptedstr.getBytes(Charset.forName("UTF-8")));
        oriVal = new String(cipher.doFinal(decrypt));
        
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
        e.printStackTrace();
    } catch (InvalidKeyException e) {
        e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
        e.printStackTrace();
    } catch (BadPaddingException e) {
        e.printStackTrace();
    }
       
       return oriVal;
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   

}

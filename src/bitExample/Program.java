package bitExample;

import java.util.Arrays;
import java.util.Comparator;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        // transposition key used to encode text
        String cryptographicKey="hfiauhfso";

        //length of the transposition key
        float cryptographicKeyLength=cryptographicKey.length();

        // text to encode
        String textToEncode="Buongiorno a tutti";

        // concate the variable of type String to the variable of the same type that contains the 
        // text to encode,  removing the spaces from the  phrase text to encode
        textToEncode=cryptographicKey+textToEncode.replace(" ","");

        // length of the text to encode
        float textToEncodeLength=textToEncode.length();

        // operation to calculate the number of rows in relation to the length of the key
        Double rowOperation=Math.ceil(textToEncodeLength/cryptographicKeyLength);

        // creation of variable of type int, that stores the number of rows calculate on line 26, rounded by excess
        int rows = (int) Math.round(rowOperation)+1;

        // initialization of Array collection of type char, in which it's stored the String value of the key converted to
        // an Array of type char
        char[] keyToChar=cryptographicKey.toCharArray();

        // METHOD TO PROCESS THE ARRAY KEY
        // ----------------------------------



        // initialization a temporary Array collection of type char, in which it's stored the String value of the
        // text converted to an Array of type char
        char[] temp=textToEncode.toCharArray();

        // initialization of an Array of type Character, that will contain the converted array of type character
        //the conversion happens through the methods associated with the String complex varialble

        //(to be specific, the array of type char is casted to type String, so that the methods associated to the type
        // can be used. Then the array now converted to a string is converted to an int type stream that extends the char
        // values, after that there is a conversion to object type Stream is that can now be converted to an Array 
        // of type Character )
        Character[] characterTextToEncode= new String(temp).chars()
                                             .mapToObj(c -> (char) c)
                                            .toArray(Character[]::new);

        // initializes a multidimensinal Array of type char, of number of columns equal to rows and 
        // number of columns equal to the length of the key
        char [][] multidimensionalArray= new char[rows][(int)cryptographicKeyLength];

        //counter to cycle for the length of the text
        int counter=0;
        
        System.out.println("Rappresentation of the key concatenated to the text to encode in columns: ");
        //for loop with nested for loop, to fill the multidimentional Array initialized on line 53
        for(int i=0; i<rows;i++){

            for(int j=0;j<(int)cryptographicKeyLength;j++){
                
                // if the counter is smaller than the length of the text to encode
                // (this is done because in the last column there could be additional iterations beyond the length 
                // set for the multidimensional array)
                if(counter<(int)textToEncodeLength){
                    multidimensionalArray[i][j]=characterTextToEncode[counter];
                    System.out.print(multidimensionalArray[i][j]);
                }
                counter++;
            }
            System.out.println();
        }



        Character[][] arrayIndex=new Character[(int)cryptographicKeyLength][1];
        for(int i=0; i<(int)cryptographicKeyLength;i++){

            int index=i;
            char convertedIndex=(char)(index+'0');
            Character[] arrayContent= {multidimensionalArray[0][i], convertedIndex};
            arrayIndex[i]= arrayContent;
        }

        //sorting
        Arrays.sort(arrayIndex, Comparator.comparing(o -> o[0]));

        StringBuffer encryptedWord=new StringBuffer();

        String alphabet="abcdefghijklmnopqrstuvwxyz";
        String alphabetUpperCase=alphabet.toUpperCase();

        char [ ] alphabetArray=alphabet.toCharArray();
        char [ ] alphabetArrayUpper=alphabetUpperCase.toCharArray();

        for(int i=0; i<arrayIndex.length;i++){
            for (int j=1;j<rows;j++){
                int sortedIndex=Integer.parseInt((Character.toString(arrayIndex[i][1])));
                boolean check=false;
                for(int k=0;k< alphabet.length();k++){
                    if(new String (String.valueOf(multidimensionalArray[j][sortedIndex])).contains(String.valueOf(alphabetArray[k]))){
                        check=true;
                    }else if(new String (String.valueOf(multidimensionalArray[j][sortedIndex])).contains(String.valueOf(alphabetArrayUpper[k]))){
                        check=true;
                    }
                }
                if(check){
                    encryptedWord.append(multidimensionalArray[j][sortedIndex]);
                }
            }

        }
        String result=encryptedWord.toString().trim();
        System.out.println(result);



        //parining and sorting
        Character[][] pair=new Character[(int)cryptographicKeyLength][(int)cryptographicKeyLength];
        for (int i=0;i<(int)cryptographicKeyLength;i++){
            pair[i]=new Character[]{cryptographicKey.charAt(i),(char)i};
        }

        Arrays.sort(pair, Comparator.comparing(o ->((Character) o[0])));

        //decryption
        StringBuffer decryptedWord= new StringBuffer();
        //cryptographicKeyLength

        int wordLength=result.length();
        float numberOfWhiteSpaces=cryptographicKeyLength - wordLength % cryptographicKeyLength,numberOfColumns=(int) Math.ceil((float)wordLength/cryptographicKeyLength);

        int ip=0;
        String[] subwords= new String[(int)cryptographicKeyLength];
        for (int i=0; i<wordLength;i++){

            if(Integer.valueOf(pair[ip][1])>=cryptographicKeyLength-numberOfWhiteSpaces){
                for(int j=0;j<numberOfColumns-1;j++){
                    decryptedWord.append(result.charAt(i+j));
                }
                i+=numberOfColumns-2;
            }else{
                for(int j=0;j<numberOfColumns;j++){
                    decryptedWord.append(result.charAt(i+j));
                }
                i+=numberOfColumns-1;
            }
            


            subwords[Integer.valueOf(pair[ip][1])]=decryptedWord.toString();

            decryptedWord.delete(0, decryptedWord.length());


            ip++;
        }
        decryptedWord.delete(0, decryptedWord.length());
        for (int i=0; i<numberOfColumns;i++){
            for(int j=0;j<cryptographicKeyLength;j++){
                if(i==numberOfColumns-1 && subwords[j].length()==numberOfColumns-1){
                    continue;

                }
                decryptedWord.append(subwords[j].charAt(i));
            }
        }

        String decryptedResult=decryptedWord.toString();
        System.out.println("decryption: "+decryptedResult);

    }
    public static void keyProcessing(){
        
    }
}


package bitExample;

import java.util.Arrays;
import java.util.Comparator;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        // transposition key used to encode text
        String cryptographicKey="vince";

        //length of the transposition key
        float cryptographicKeyLength=cryptographicKey.length();

        // text to encode
        String textToEncode="la cavalleria deve attaccare sulla sinistra";

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

        Character[][] pairSortedKeyIndex=keyProcessing((int)cryptographicKeyLength,keyToChar);

        for(Character[] keyIndex:pairSortedKeyIndex){
            System.out.println(keyIndex[0]+" "+(int)keyIndex[1]);
        }

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

        // initializes a multidimensional Array of type char, of number of columns equal to rows and
        // number of columns equal to the length of the key
        char [][] multidimensionalArray= new char[rows][(int)cryptographicKeyLength];

        //counter to cycle for the length of the text
        int counter=0;
        
        System.out.println("Representation of the key concatenated to the text to encode in columns: ");
        //for loop with nested for loop, to fill the multidimensional Array initialized on line 53
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


        StringBuffer encryptedWord=new StringBuffer();

        for(int i=0; i<pairSortedKeyIndex.length;i++){
            for (int j=1;j<rows;j++){

                int sortedIndex=Character.getNumericValue(pairSortedKeyIndex[i][1]);

                boolean check=false;

                if(Character.isLetterOrDigit(multidimensionalArray[j][sortedIndex])){
                    check=true;
                }

                if(check){
                    encryptedWord.append(multidimensionalArray[j][sortedIndex]);
                }
            }

        }
        String encryptionResult=encryptedWord.toString().trim();
        System.out.println(encryptionResult);



        //parining and sorting
        Character[][] pair=new Character[(int)cryptographicKeyLength][(int)cryptographicKeyLength];
        for (int i=0;i<(int)cryptographicKeyLength;i++){
            pair[i]=new Character[]{cryptographicKey.charAt(i),(char)i};
        }

        Arrays.sort(pair, Comparator.comparing(o ->((Character) o[0])));

        //decryption
        StringBuffer decryptedWord= new StringBuffer();
        //cryptographicKeyLength

        int wordLength=encryptionResult.length();
        float numberOfWhiteSpaces=cryptographicKeyLength - wordLength % cryptographicKeyLength,numberOfColumns=(int) Math.ceil((float)wordLength/cryptographicKeyLength);

        int ip=0;
        String[] subwords= new String[(int)cryptographicKeyLength];
        for (int i=0; i<wordLength;i++){

            if(Integer.valueOf(pair[ip][1])>=cryptographicKeyLength-numberOfWhiteSpaces){
                for(int j=0;j<numberOfColumns-1;j++){
                    decryptedWord.append(encryptionResult.charAt(i+j));
                }
                i+=numberOfColumns-2;
            }else{
                for(int j=0;j<numberOfColumns;j++){
                    decryptedWord.append(encryptionResult.charAt(i+j));
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
    static Character[][] keyProcessing(int keyLength,char[] key){

        // Sorting of the Array variable of type char containing the key

        // initialization of temporary variable to store values
        char temp;

        // initialization of Array of type char, in which a copy of the original Array of the same type containing the cryptographic key is going to be contained
        char[] copyOfKey=Arrays.copyOf(key, keyLength);

        // initialization of a counter variable of type integer, that is going to be used to determine if the character in position of index min
        // is greated than the characters contained in the rest of the array.
        int min;

        for(int i = 0;i < keyLength;i++){
            min=i;
            for(int j = i; j < keyLength;j++){

                //if the character in the position min (in ASCII code) is greater than the character in the position j
                //change the index min, associated to the smaller character
                if(copyOfKey[min]>copyOfKey[j]){
                    min=j;
                }
            }

            // if the index min used to store the index of a character that is smaller, is not equal to the i,
            // than a greater number (to the one in position i of the array) has been found and  we can swap
            // them accordingly
            if(min!=i){
                temp=copyOfKey[i];
                copyOfKey[i]=copyOfKey[min];
                copyOfKey[min]=temp;
            }
            //System.out.print(copyOfKey[i]);
        }

        //extrapolation of positions of the (NOW) ordered characters in the array

        //initialization of a multidimensional Array of type Character or rows equal to the keyLength(5) and of 2 columns, to contain
        //the ordered character and its modified index from the original unordered Array of type character
        Character[][] pairSortedKeyIndex=new Character[keyLength][1];

        for(int i=0;i<keyLength;i++){
            for (int j=0;j<keyLength;j++){

                //if the ordered character in the Array of ordered characters(copyOfKey) is equal to the character in the unordered array,
                //take the ordered character and the unordered index associated to it and add it to the pairSortedKeyIndex, in a
                //multidimensional array character, index
                if(copyOfKey[i]==key[j]){
                    //pair[i]=new Character[]{cryptographicKey.charAt(i),(char)i};
                    pairSortedKeyIndex[i]= new Character[]{copyOfKey[i], (char)((char)j+'0')};
                }
            }
        }

        //for(Character[] keyIndex:pairSortedKeyIndex){
        //    System.out.println(keyIndex[0]+" "+(int)keyIndex[1]);
        //}

        return pairSortedKeyIndex;
    }
}


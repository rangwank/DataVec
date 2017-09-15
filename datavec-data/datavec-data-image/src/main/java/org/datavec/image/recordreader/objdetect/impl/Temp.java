/*
 *  * Copyright 2017 Skymind, Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 */

package org.datavec.image.recordreader.objdetect.impl;

import java.io.File;
import java.io.FileInputStream;

public class Temp {

    public static void main(String[] args) throws Exception {

        String path = "E:\\Data\\COCO\\annotations\\instances_train2017.json";

        FileInputStream fis = new FileInputStream(new File(path));

        byte[] bytes = new byte[256];
        int read = 1;
//        for( int i=0; i<100 && read > 0; i++ ){
        int printed = 0;
        while(read > 0 && printed < 100){
            read = fis.read(bytes);
            if(read > 0){
                String s = new String(bytes);
//                if(s.contains("annotation")){
                if(s.contains("bbox")){
                    System.out.println(s);
                    printed++;
                }
            }
        }

    }

}

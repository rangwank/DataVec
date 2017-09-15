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

import lombok.NonNull;
import org.datavec.image.recordreader.objdetect.ClassFilter;
import org.datavec.image.recordreader.objdetect.ImageObject;
import org.datavec.image.recordreader.objdetect.ImageObjectLabelProvider;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CocoLabelProvider implements ImageObjectLabelProvider {

    private static final List<String> OBJECT_CLASSES = Collections.unmodifiableList(
            Arrays.asList(
                    "person", "bicycle", "car", "motorcycle", "airplane", "bus", "train", "truck", "boat",
                    "traffic light", "fire hydrant", "street sign", "stop sign", "parking meter", "bench",
                    "bird", "cat", "dog", "horse", "sheep", "cow", "elephant", "bear", "zebra", "giraffe",
                    "hat", "backpack", "umbrella", "shoe", "eye glasses", "handbag", "tie", "suitcase",
                    "frisbee", "skis", "snowboard", "sports ball", "kite", "baseball bat", "baseball glove",
                    "skateboard", "surfboard", "tennis racket", "bottle", "plate", "wine glass", "cup",
                    "fork", "knife", "spoon", "bowl", "banana", "apple", "sandwich", "orange", "broccoli",
                    "carrot", "hot dog", "pizza", "donut", "cake", "chair", "couch", "potted plant", "bed",
                    "mirror", "dining table", "window", "desk", "toilet", "door", "tv", "laptop", "mouse",
                    "remote", "keyboard", "cell phone", "microwave", "oven", "toaster", "sink",
                    "refrigerator", "blender", "book", "clock", "vase", "scissors", "teddy bear",
                    "hair drier", "toothbrush", "hair brush"));

    private final String baseDirectory;
    private final ClassFilter classFilter;
    private final boolean inferLabels;

    public CocoLabelProvider(@NonNull String baseDirectory, ClassFilter classFilter, boolean inferLabels){

        //TODO check folder structure

        this.baseDirectory = baseDirectory;
        this.classFilter = classFilter;
        this.inferLabels = inferLabels;

        //Initialize: read/parse JSON
    }

    @Override
    public List<String> classLabels() {
        if(inferLabels){
            return null;
        } else if(classFilter != null){
            List<String> filtered = new ArrayList<>();
            for(String s : OBJECT_CLASSES){
                if(classFilter.acceptClass(s)){
                    filtered.add(s);
                }
            }
            return filtered;
        }
        return OBJECT_CLASSES;
    }

    @Override
    public List<ImageObject> getImageObjectsForPath(String path) {
        return null;
    }

    @Override
    public List<ImageObject> getImageObjectsForPath(URI uri) {
        return null;
    }
}

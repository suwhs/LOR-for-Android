/*
 * Copyright 2015 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.voxelplanet.lorforandroid.util;

import net.voxelplanet.lorforandroid.model.Comment;

import java.util.List;

public class CommentUtils {
    public static Comment getParent(List<Comment> comments, Comment child) {
        for (Comment comment : comments) {
            if (comment.getId().equals(child.getReply().getId())) {
                return comment;
            }
        }
        return new Comment();
    }

    public static boolean isReply(Comment comment) {
        return comment.getReply() != null;
    }
}

/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.9
 */
public interface AccountTotals extends TwitterResponse, java.io.Serializable {
    /**
     * Returns the number of total updates.
     *
     * @return the number of total updates
     */
    int getUpdates();

    /**
     * Returns the number of total followers.
     *
     * @return the number of total followers
     */
    int getFollowers();

    /**
     * Returns the number of total favorites.
     *
     * @return the number of total favorites
     */
    int getFavorites();

    /**
     * Returns the number of total friends.
     *
     * @return the number of total friends
     */
    int getFriends();
}

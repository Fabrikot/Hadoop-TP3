package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UserRecommendation implements WritableComparable<UserRecommendation> {
    private String userId;         // Utilisateur de la recommandation
    private String recommendedId;  // User recommandé
    private int commonFriends;    // Nombre d'amis communs

    public UserRecommendation() {
        this.userId = "";
        this.recommendedId = "";
        this.commonFriends = 0;
    }

    public UserRecommendation(String userId, String recommendedId, int commonFriends) {
        this.userId = userId;
        this.recommendedId = recommendedId;
        this.commonFriends = commonFriends;
    }

    public String getUserId() { return userId; }
    public String getRecommendedId() { return recommendedId; }
    public int getCommonFriends() { return commonFriends; }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(userId);
        out.writeUTF(recommendedId);
        out.writeInt(commonFriends);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        userId = in.readUTF();
        recommendedId = in.readUTF();
        commonFriends = in.readInt();
    }

    @Override
    public int compareTo(UserRecommendation o) {
        // Trier d'abord par le nombre d'amis communs
        int compare = -Integer.compare(this.commonFriends, o.commonFriends);
        // En cas d'égalité, trier par ID d'utilisateur recommandé
        if (compare != 0) return compare;
        return this.recommendedId.compareTo(o.recommendedId);
    }

    @Override
    public String toString() {
        return recommendedId + "(" + commonFriends + ")";
    }
}
package bili.study.base;

import java.util.ArrayList;
import java.util.List;

public class Coins {

    public static void main(String[] args) {
        int[] coins = {1,2,5};
        coinChange(coins, 20);
    }

    static int coinChange(int[] coins, int amount) {

        int[] dp = new int[amount + 1];
        for (int i = 0; i < amount + 1; i++) {
            dp[i] = i;
        }
        for (int i = 0; i < dp.length; i++) {
            for (int coin: coins) {
                // 子问题无解，跳过
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], 1 + dp[i -coin]);
            }
        }
        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }
}

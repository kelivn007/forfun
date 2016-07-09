package hw.app.notelist.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.baidu.xcloud.account.AccountInfo;
import com.baidu.xcloud.account.BaiduOAuth;
import com.baidu.xcloud.account.BaiduOAuthResponse;
import com.baidu.xcloud.account.OAuthListener;

/**
 * 
 * @author huangwei05
 * 
 */
public class NoteAccountManager {

    public static final String APIKEY = "mBAuU4heZvN178rN8IulKXef";
    public static final String APISECRET = "bD8pYNGdqon4gcL103763mk3qeb4XITD";
    public static final String APPID = "2998711";
    public static final String ROOTPATH = "/apps/xcloudDir";

    private final static String PRE_NAME = "cloudnote";
    private final static String PRE_USERNAME = "pre_username";
    private final static String PRE_USERTOKEN = "pre_usertoken";

    public static interface ILoginListener {
        public void onLogin(AccountInfo accountInfo);

        public void onLogout();
    }

    public static void startLogin(final Activity activity, final ILoginListener listener) {
        BaiduOAuth oauth = new BaiduOAuth();
        OAuthListener oauthListener = new OAuthListener() {

            @Override
            public void onException(String msg) {
                // TODO Auto-generated method stub
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "login Exception--检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(activity, "login Cancel", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onComplete(BaiduOAuthResponse oauth) {
                // TODO Auto-generated method stub
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setAccessToken(oauth.getAccessToken());
                accountInfo.setUid(oauth.getUserName());

                Editor editor = activity.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE).edit();
                editor.putString(PRE_USERNAME, oauth.getUserName());
                editor.putString(PRE_USERTOKEN, oauth.getAccessToken());
                editor.commit();
                listener.onLogin(accountInfo);
            }

            @Override
            public void onStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

        };
        oauth.startGenericLogin(activity, APIKEY, null, oauthListener);
    }

    public static void logout(Context context, ILoginListener listener) {
        Editor editor = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PRE_USERNAME, "");
        editor.putString(PRE_USERTOKEN, "");
        editor.commit();
        listener.onLogout();
    }

    public static String getUserName(Context context) {
        SharedPreferences prefer = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return prefer.getString(PRE_USERNAME, "");
    }

    public static String getUserToken(Context context) {
        SharedPreferences prefer = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return prefer.getString(PRE_USERTOKEN, "");
    }

    private NoteAccountManager() {
    }

}

package com.softigress.magicsigns._system.FireBase.Authentication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.softigress.magicsigns.Activities.MainActivity.MainActivityUtils;
import com.softigress.magicsigns.R;
import com.softigress.magicsigns._system.FireBase.Analytics.AnalyticsManager;
import com.softigress.magicsigns._system.FireBase.DataBase.DataBaseManager;
import com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos.FileInfo;
import com.softigress.magicsigns._system.FireBase.RemoteConfig.FileInfos.FileInfoJpg;
import com.softigress.magicsigns._system.FireBase.Storage.LoadFileTasks.IAsyncLoadFile;
import com.softigress.magicsigns._system.Settings.CurrentSettings;
import com.softigress.magicsigns._system.Tasks.AsyncBase;
import com.softigress.magicsigns._system.Tasks.IAsyncParams;
import com.softigress.magicsigns._system.Utils.MetrixUtils;
import com.softigress.magicsigns._system.Utils.PaintUtils;
import com.softigress.magicsigns._system.Utils.Utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.google.firebase.crash.FirebaseCrash;

public class AuthManager {

    private static final int STATUS_CONNECTION_DISABLED = 0;
    private static final int STATUS_CONNECTION_ENABLED = 1;
    private int connectionStatusId = STATUS_CONNECTION_ENABLED; // по умолчанию

    private static final int SIGN_IN_GOOGLE = 1;
    private static final int SIGN_IN_FACEBOOK = 64206;
    private static final int SIGN_IN_TWITTER = 3;
    public static final int PROVIDER_ID_FIREBASE = -563351033;//"firebase";
    public static final int PROVIDER_ID_GOOGLE = -1536293812;//"google.com";
    public static final int PROVIDER_ID_FACEBOOK = -364826023; //"facebook.com";

    private static final boolean isLinkWith = false; // связывать аккаунт firebase с другими аккаунтами
    private static final boolean isAutoSignIn = true; // автоматически входить под выбранным аккаунтом

    private static final boolean isCheckInternetConnection = true; // периодическая проверка связи
    private static final boolean isToast = false; // отображать сообщения
    private static final int connectTimeout = 10000; // ждать соединение 10 секунд
    private static final int readTimeout = 5000; // ждать ответ 5 секунд
    private static final int checkOnPeriod = 30000; // период проверки, если связь есть (30 sec)
    private static final int checkOffPeriod = 5000; // период проверки, если связи нет (5 sec)
    private static int checkPeriod = checkOffPeriod; // период проверки

    private Activity activity;
    private ConnectivityManager connectivityManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private List<? extends UserInfo> providerData;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    //private GoogleApiClient googleApiClient;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager facebookCallbackManager;

    private final Paint paintConnection0ff, paintConnectionOn;

    public AuthManager(Activity activity, ConnectivityManager connectivityManager) {
        int step = 0;
        try {
            this.activity = activity;
            this.connectivityManager = connectivityManager;
            step = 1;
            //region google options
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("190691572751-b5q6u0mqrd5ehplfdaujjv5ja08ndeb3.apps.googleusercontent.com")   // web
                    .requestProfile()
                    .requestEmail()
                    .build();
            step = 2;
            googleSignInClient = GoogleSignIn.getClient(activity.getApplicationContext(), gso);
            //step = 3;
            /*googleApiClient = new GoogleApiClient
                    .Builder(activity.getApplicationContext())
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Utils.Toast("google connection error: " + connectionResult.getErrorMessage());
                        }
                    })
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            // connected
                        }
                        @Override
                        public void onConnectionSuspended(int i) {
                            // connection suspended
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build(); */
            //googleApiClient.connect();
            //endregion

            //region facebook options
            step = 4;
            facebookCallbackManager = CallbackManager.Factory.create();
            step = 5;
            LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) { onSignInWithFacebook(loginResult.getAccessToken()); }
                @Override
                public void onCancel() { }
                @Override
                public void onError(FacebookException error) { Utils.Toast("facebook login error: " + error.getMessage()); }
            });
            //endregion

            step = 6;
            getFirebaseAuth();
            step = 7;

        } catch (Throwable t) {
            Utils.CrashReport("AuthManager [" + step + "]", t);
        }

        paintConnection0ff = PaintUtils.getPaint(255, 255, 0, 0);
        paintConnectionOn = PaintUtils.getPaint(255, 0, 255, 0);
    }

    //region Connection status
    public boolean isConnected() { return connectionStatusId == STATUS_CONNECTION_ENABLED; }

    private long startTicks;
    public void checkConnectionAsync() {
        try {
            if (CurrentSettings.isCheckConnection) {
                long ticks = SystemClock.elapsedRealtime();
                if (startTicks == 0)
                    startTicks = ticks;
                if (ticks - startTicks > checkPeriod) {
                    new AsyncBase<>(
                            new IAsyncParams<Integer, Boolean>() {
                                @Override public Boolean run(Integer[] params) { return checkConnection(); }
                                @Override
                                public void onFinish(Boolean result) {
                                    checkPeriod = result ? checkOnPeriod : checkOffPeriod;
                                    int oldStatusId = connectionStatusId;
                                    int newStatusId = result ? STATUS_CONNECTION_ENABLED : STATUS_CONNECTION_DISABLED;
                                    if (oldStatusId != newStatusId) { // при смене статуса
                                        connectionStatusId = newStatusId;
                                        if (result)
                                            MainActivityUtils.doConnectionOn();
                                        else
                                            MainActivityUtils.doConnectionOff();
                                    }
                                    MainActivityUtils.doConnection(result);
                                }
                            }
                    ).execute();
                    startTicks = 0;
                }
            }
        } catch (Throwable t) {
            Utils.CrashReport("AuthManager.checkConnectionAsync", t);
        }
    }

    // не на всех устройствах работает
    private boolean checkInternetConnection() {
        boolean res = false;
        if (isCheckInternetConnection) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8"); // Google DNS
                res = ipProcess.waitFor() == 0;
            } catch (Throwable t) {
                Utils.CrashReport("checkInternetConnection", t);
            }
        } else
            res = true;
        return res;
    }

    private boolean checkConnection() {
        boolean res = false;
        try {
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    try {
                        if (isCheckInternetConnection) {
                            URL url = new URL("https://www.google.com/");
                            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                            //urlc.setRequestProperty("User-Agent", "test");
                            urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(connectTimeout);
                            urlc.setReadTimeout(readTimeout);
                            urlc.connect();
                            res = urlc.getResponseCode() == 200; // ресурс доступен
                        } else
                            res = true;
                    } catch (Throwable t) {
                        Utils.Toast(t.getMessage());
                        //Utils.CrashReport("AuthManager.checkConnection no internet", t);
                    }
                }
            }
        } catch (Throwable t) {
            //Utils.CrashReport("AuthManager.checkConnection", t);
        } finally {
            return res;
        }
    }

    public void drawConnectionStatus(Canvas c) {
        float d = MetrixUtils.screen_metrix_width * .015f;
        if (Utils.authManager.isConnected())
            c.drawCircle(MetrixUtils.screen_metrix_width - d, d, d / 2, paintConnectionOn);
        else
            c.drawCircle(MetrixUtils.screen_metrix_width - d, d, d / 2, paintConnection0ff);
        // user
        /*if (firebaseUser != null) {
            TextUtils.WriteTextRight(c, MetrixUtils.screen_metrix_width - 2 * d, 3 * d, getUserId(), Typeface.DEFAULT);
            TextUtils.WriteTextRight(c, MetrixUtils.screen_metrix_width - 2 * d, 6 * d, getCurrentProviderName(), Typeface.DEFAULT);
            TextUtils.WriteTextRight(c, MetrixUtils.screen_metrix_width - 2 * d, 9 * d, getUserName(), Typeface.DEFAULT);
        }*/
    }
    //endregion

    private FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth != null) {
            if (firebaseAuthStateListener == null) {
                firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        setFirebaseUser(firebaseAuth.getCurrentUser());
                        if (isAutoSignIn)
                            autoSignIn();
                    }
                };
                firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
            }
        }
        return firebaseAuth;
    }

    //region Firebase User
    private void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
        if (firebaseUser != null)
            this.providerData = firebaseUser.getProviderData();

    }
    public String getUserId() {
        return firebaseUser != null ? firebaseUser.getUid() : null;
    }
    public String getUserEmail() {
        return firebaseUser != null ? firebaseUser.getEmail() : null;
    }
    public String getUserName() {
        String name = null;
        if (providerData != null)
            for (UserInfo userInfo : providerData)
                name = userInfo.getDisplayName();
        return name != null ? name : "";
    }
    private Uri getUserPhotoUri() {
        Uri uri = null;
        if (providerData != null)
            for (UserInfo userInfo : providerData)
                uri = userInfo.getPhotoUrl();
        return uri;
    }
    public void getUserPhoto(IAsyncLoadFile asyncLoadFile) {
        String id = getUserId();
        Uri photoUri = getUserPhotoUri(); // user photo uri
        if (id != null && photoUri != null) {
            String url = photoUri.toString();
            FileInfo fileInfo = new FileInfoJpg(url, id); // id - FileName
            Utils.storageManager.downloadFileDirectly(fileInfo, asyncLoadFile);
        }
    }
    public String getCurrentProviderName() {
        String providerId = null;
        if (providerData != null)
            for (UserInfo userInfo : providerData)
                providerId = userInfo.getProviderId();
        return providerId != null ? providerId : "";
    }
    public int getCurrentProviderHash() { return getCurrentProviderName().hashCode(); }
    //endregion

    //region Sing in
    public void singInAnonymously(String newUserName) {
        try {
            if (firebaseAuth != null) {
                setFirebaseUser(firebaseAuth.getCurrentUser());
                if (firebaseUser == null) {
                    // сохраним имя пользователя в настройках
                    Utils.setUserName(newUserName);
                    Utils.saveSettings();
                    onComplete(firebaseAuth.signInAnonymously(), newUserName); // sign in anonymously
                } else
                    onAuthChanged();
            }
        } catch (Throwable t) {
            Utils.CrashReport("AuthManager.signInAnonymously", t);
        }
    }

    private boolean isTryAutoSignIn = false; // признак попытки автоматического вохода
    private void autoSignIn() {
        if (!isTryAutoSignIn) {
            isTryAutoSignIn = true;
            Utils.LogEvent(AnalyticsManager.MS_EVENT_ACTION, "act_auto_signin");
            Integer providerHash = Utils.getProviderHash();
            if (providerHash != null) {
            if (providerHash.equals(PROVIDER_ID_FIREBASE))
                onAuthChanged();
            else if (providerHash.equals(PROVIDER_ID_GOOGLE))
                singInGoogle();
            else if (providerHash.equals(PROVIDER_ID_FACEBOOK))
                singInFacebook();
            }
        }
    }
    public void singInGoogle() { singIn(SIGN_IN_GOOGLE); }
    public void singInFacebook() { singIn(SIGN_IN_FACEBOOK); }
    public void singInTwitter() { singIn(SIGN_IN_TWITTER); }

    private void singIn(int authTypeId) {
        switch (authTypeId) {
            case SIGN_IN_GOOGLE:
                Intent signInGoogleIntent = googleSignInClient.getSignInIntent();//Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                activity.startActivityForResult(signInGoogleIntent, SIGN_IN_GOOGLE);
                break;
            case SIGN_IN_FACEBOOK:
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email")); // "user_friends"
                break;
            /*case SIGN_IN_TWITTER:
                Intent signInTwitterIntent = AuthUI.getInstance().createSignInIntentBuilder().setProviders(
                        Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build())).build();
                activity.startActivityForResult(signInTwitterIntent, SIGN_IN_TWITTER);
                break;*/
        }
    }

    public void signOut() {
        Utils.LogEvent(AnalyticsManager.MS_EVENT_ACTION, "act_signout");
        //String providerName = getCurrentProviderName();
        //if (isLinkWith) {
        //    // отвязываем аккаунт firebase от связанного аккаунта
        //    if (firebaseUser != null)
        //        onComplete(firebaseUser.unlink(providerName));
        //} else {
            FirebaseAuth fAuth = getFirebaseAuth();
            if (fAuth != null) {
                fAuth.signOut();
                setFirebaseUser(fAuth.getCurrentUser());
                onAuthChanged();
            }
        //}
    }

    private void onAuthChanged() {
        try {
            Utils.setProviderHash(getCurrentProviderHash());
            MainActivityUtils.doAuthChanged();
        } catch (Throwable t) {
            Utils.CrashReport("AuthManager.onAuthChanged", t);
        }
    }
    //endregion

    private static boolean isAuthRequest(int requestCode) {
        return requestCode == SIGN_IN_GOOGLE || requestCode == SIGN_IN_FACEBOOK || requestCode == SIGN_IN_TWITTER;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAuthRequest(requestCode)) {
            if (requestCode == SIGN_IN_GOOGLE) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                onSingInWithGoogle(result);
                //Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                //onSingInWithGoogle(task);
            } else if (requestCode == SIGN_IN_FACEBOOK)
                facebookCallbackManager.onActivityResult(requestCode, resultCode, data);

            /*else if (requestCode == SIGN_IN_TWITTER) {
                // ...
            }*/
        }
    }

    //region onSignIn
    private void onSingInWithGoogle(GoogleSignInResult result) {
        try {
            if (result != null) {
                if (result.isSuccess()) {
                    if (isToast)
                        Utils.Toast("RESULT SUCCESS (result)");
                    GoogleSignInAccount googleAccount = result.getSignInAccount();
                    if (googleAccount != null) {
                        // учетные данные google аккаунта
                        AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
                        FirebaseAuth fAuth = getFirebaseAuth();
                        if (fAuth != null) {
                            if (isLinkWith) {
                                // связываем firebase аккаунт с google аккаунтом
                                if (fAuth.getCurrentUser() != null)
                                    onComplete(fAuth.getCurrentUser().linkWithCredential(credential));
                            } else
                                // входим в google аккаунт
                                onComplete(fAuth.signInWithCredential(credential));
                        }
                    }
                } else {
                    if (isToast)
                        Utils.Toast("NO RESULT (result)");
                }
            }
        } catch (Throwable t) {
            Utils.CrashReport("AuthManager.onSingInWithGoogle(result)", t);
        }
    }

    private void onSingInWithGoogle(Task<GoogleSignInAccount> task) {
        try {
            if (task != null) {
                try {
                    GoogleSignInAccount googleAccount = task.getResult(ApiException.class);
                    if (isToast)
                        Utils.Toast("RESULT SUCCESS (task)");
                    if (googleAccount != null) {
                        // учетные данные google аккаунта
                        AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
                        FirebaseAuth fAuth = getFirebaseAuth();
                        if (fAuth != null) {
                            if (isLinkWith) {
                                // связываем firebase аккаунт с google аккаунтом
                                if (fAuth.getCurrentUser() != null)
                                    onComplete(fAuth.getCurrentUser().linkWithCredential(credential));
                            } else
                                // входим в google аккаунт
                                onComplete(fAuth.signInWithCredential(credential));
                        }
                    }
                } catch (Throwable t){
                    Utils.CrashReport("AuthManager.onSingInWithGoogle(task)", t);
                }
            }
        } catch (Throwable t) {
            Utils.CrashReport("AuthManager.onSingInWithGoogle", t);
        }
    }

    private void onSignInWithFacebook(AccessToken token) {
        try {
            // учетные данные facebook аккаунта
            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            FirebaseAuth fAuth = getFirebaseAuth();
            if (fAuth != null) {
                if (isLinkWith) {
                    // связываем firebase аккаунт с аккаунтом facebook
                    if (fAuth.getCurrentUser() != null)
                        onComplete(fAuth.getCurrentUser().linkWithCredential(credential));
                } else
                    // входим в аккаунт facebook
                    onComplete(fAuth.signInWithCredential(credential));
            }
        } catch (Throwable t) {
            Utils.CrashReport("AuthManager.onSignInWithFacebook", t);
        }
    }
    //endregion

    //region onComplete
    private void onComplete(Task<AuthResult> task) { onComplete(task, null); }

    private void onComplete(Task<AuthResult> task, final String newUserName) {
        final int oldHash = getCurrentProviderHash();
        final String oldUserId = getUserId(); // firebase account userId
        if (task != null) {
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception ex) {
                    //Utils.CrashReport("AuthManager.onFailureListener", ex);
                    if (isToast)
                        Utils.Toast("onFailureListener");
                }
            }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    try {
                        //if (task != null) {
                            if (task.isSuccessful()) {
                                AuthResult res = task.getResult();
                                if (res != null) {
                                    setFirebaseUser(res.getUser());
                                    String newUserId = getUserId();
                                    String providerName = getCurrentProviderName();
                                    String userName = newUserName != null ? newUserName : getUserName();

                                    // перепривяжем listener пользователя
                                    Utils.dataBaseManager.setUserListenerOff(oldUserId);
                                    Utils.dataBaseManager.setUserListenerOn();
                                    Utils.dataBaseManager.setUserVideoCountsListenerOff(oldUserId);
                                    Utils.dataBaseManager.setUserVideoCountsListenerOn();

                                    // проапдейтим информацию ползователя в БД
                                    Map vals = new HashMap<String, Object>();
                                    vals.put(DataBaseManager.DB_USER_SCORE_INFO_PROVIDER, providerName);// проапдейтим название провайдера в БД
                                    vals.put(DataBaseManager.DB_USER_SCORE_INFO_NAME, userName); // проапдейтим имя пользователя в БД
                                    Utils.dataBaseManager.updateUserValues(newUserId, vals);

                                    // при переходе с firebase аккаунта на другой (google, facebook, ...)
                                    // копируем данные аккаунта firebase в новый аккаунт
                                    if (oldHash == PROVIDER_ID_FIREBASE && oldUserId != null)
                                        Utils.dataBaseManager.copyUserScoreInfo(oldUserId, newUserId);
                                }
                            } else {
                                Throwable t = task.getException();
                                if (t != null)
                                    throw t;
                            }
                        //}
                    } catch (FirebaseAuthUserCollisionException ex) { // если аккаунт уже связан с другим устройством
                        Utils.CrashReport("AuthManager.onCompleteListener.onComplete 1", ex);
                        Utils.Toast(R.string.dlg_Login_CredetialAlreadyInUse);
                    } catch (FirebaseNetworkException ex) {
                        Utils.CrashReport("AuthManager.onCompleteListener.onComplete 2", ex);
                        //Utils.Toast(Utils.getRes(R.string.dlg_Login_NetworkError));
                    } catch (Throwable t) {
                        Utils.CrashReport("AuthManager.onCompleteListener.onComplete 3", t);
                        //Utils.Toast("AuthManager - onComplete AuthResult", t);
                    } finally {
                        onAuthChanged();
                    }
                }
            });
        }
    }
    //endregion

    public void recycle() {
        try {
            if (connectivityManager != null)
                connectivityManager = null;
            //if (googleApiClient != null)
            //    googleApiClient.disconnect();
            //if (googleSignInClient != null)
            //    googleSignInClient.asGoogleApiClient().disconnect();
            if (facebookCallbackManager != null) {
                LoginManager.getInstance().unregisterCallback(facebookCallbackManager);
                facebookCallbackManager = null;
            }
            if (firebaseAuth != null && firebaseAuthStateListener != null)
                firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
        } catch (Throwable t) {
            Utils.CrashReport("AuthManager.recycle", t);
        }
    }
}

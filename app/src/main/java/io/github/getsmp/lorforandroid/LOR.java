package io.github.getsmp.lorforandroid;

import android.app.Application;
import android.content.ContextWrapper;
import android.os.AsyncTask;

import su.whs.watl.text.HyphenLineBreaker;
import su.whs.watl.text.LineBreaker;
import su.whs.watl.text.TextLayout;
import su.whs.watl.text.hyphen.HyphenPattern;
import su.whs.watl.text.hyphen.PatternsLoader;

/**
 * Created by igor n. boulliev on 10.01.17.
 */

public class LOR extends Application {

    private LineBreaker mLineBreaker = null;

    @Override
    public void onCreate() {
        super.onCreate();
        preloadHyphenationRules();
    }

    private void preloadHyphenationRules() {
        new AsyncTask<Void,Void,LineBreaker>() {

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected LineBreaker doInBackground(Void... params) {
                /** loading patterns from assets **/
                PatternsLoader loader = PatternsLoader.getInstance(getApplicationContext());
                HyphenPattern pat_en = loader
                        .getHyphenPatternAssets("en_us.hyphen.dat");
                HyphenPattern pat_ru = loader
                        .getHyphenPatternAssets("ru.hyphen.dat");
                if (pat_en!=null) {
                    LineBreaker elb = HyphenLineBreaker.getInstance(pat_en);
                    LineBreaker rlb = HyphenLineBreaker.getInstance(pat_ru);
                    return new DualLineBreaker(elb,rlb);
                }

                return null;
            }

            @Override
            protected void onPostExecute(LineBreaker result) {
                mLineBreaker = result;
            }
        }.execute();
    }

    public LineBreaker getLineBreaker() { return mLineBreaker == null ? new TextLayout.DefaultLineBreaker() : mLineBreaker; }

    public static LOR from(ContextWrapper context) {
        if (context instanceof LOR) return (LOR)context;
        return (LOR)context.getApplicationContext();
    }

    /** DualLineBreaker **/
    public class DualLineBreaker extends LineBreaker {
        private LineBreaker en;
        private LineBreaker ru;

        public DualLineBreaker(LineBreaker en, LineBreaker ru) {
            super();
            this.en = en;
            this.ru = ru;
        }

        @Override
        public int nearestLineBreak(char[] chars, int i, int i1, int i2) {
            if (isRu(chars[i]))
                return ru.nearestLineBreak(chars,i,i1,i2);
            return en.nearestLineBreak(chars,i,i1,i2);
        }

        private boolean isRu(char ch) {
            // TODO: implement selector depends on
            return true;
        }
    }
}

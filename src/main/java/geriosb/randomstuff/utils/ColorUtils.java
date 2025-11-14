package geriosb.randomstuff.utils;

public class ColorUtils {
    public static int TweenColor(int color1, int color2, double time){
        if (time > 1) time = 1;
        if (time < 0) time = 0;
        short alpha = (short) ((color1 & 0xff000000) >> 24);
        short red = (short) ((color1 & 0xff0000) >> 16);
        short green = (short) ((color1 & 0xff00) >> 8);
        short blue = (short) (color1 & 0xff);
        short alphaoff = (short) (((color2 & 0xff000000) >> 24)-alpha);
        short redoff = (short) (((color2 & 0xff0000) >> 16)-red);
        short greenoff = (short) (((color2 & 0xff00) >> 8)-green);
        short blueoff = (short) ((color2 & 0xff)-blue);
        alpha = (short) (alpha+(alphaoff*time));
        red = (short) (red+(redoff*time));
        green = (short) (green+(greenoff*time));
        blue = (short) (blue+(blueoff*time));
        int outcolor = blue&0xff;
        outcolor |= ((int)green<<8)&0xff00;
        outcolor |= ((int)red<<16)&0xff0000;
        outcolor |= ((int)alpha<<24)&0xff000000;
        return outcolor;
    }
    public static int TweenColor(int color1, int color2, float time){
        return TweenColor(color1,color2,(double)time);
    }
    public static int TweenColor(int color1, int color2, char time){ // why no unsigned int
        return TweenColor(color1,color2,((double)time)/65535);
    }
}

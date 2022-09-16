package xyz.doikki.videoplayer;

import android.content.Context;

import xyz.doikki.videoplayer.MediaPlayer;
import xyz.doikki.videoplayer.sys.SysMediaPlayer;
import xyz.doikki.videoplayer.sys.SysMediaPlayerFactory;

/**
 * 此接口使用方法：
 * 1.继承{@link MediaPlayer}扩展自己的播放器。
 * 2.继承此接口并实现{@link #create(Context)}，返回步骤1中的播放器。
 * 可参照{@link SysMediaPlayer}和{@link SysMediaPlayerFactory}的实现。
 */
public abstract class MediaPlayerFactory<P extends MediaPlayer> {

    /**
     *
     * @param context 注意内存泄露：内部尽可能使用context.getApplicationContext()
     * @return
     */
    public abstract P create(Context context);
}

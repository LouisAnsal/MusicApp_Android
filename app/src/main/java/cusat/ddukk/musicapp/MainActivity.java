package cusat.ddukk.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer m;
    String songs[]={"Lane 8 - Little Voices (Original Mix)","Chris Llopis - Platonic Shower (Dmitry Molosh Remix)","Darin Epsilon & Matan Caspi - Thousand Winds (Wally Lopez Remix)","Lane 8 - Clarify feat. Fractures (Tinlicker Remix)","Lane 8 - Daya (Fairchild Remix)"};
    ArrayList songr = new ArrayList<String>();
    Field[] fields= R.raw.class.getFields();
    Thread upseek;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView play= findViewById(R.id.play);
        ImageView pause= findViewById(R.id.pause);
        ImageView stop= findViewById(R.id.stop);

        TextView title= findViewById(R.id.songt);
        title.setSelected(true);
        TextView songTotalDurationLabel = findViewById(R.id.stotdur);
        TextView songCurrentDurationLabel = findViewById(R.id.scurdur);

        SeekBar seekbar = findViewById(R.id.seekb);

        for(int i=0; i<fields.length; i++)
            {
                songr.add(fields[i].getName());
            }

        ListView songlist=findViewById(R.id.songlist);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,songs);
        songlist.setAdapter(adapter);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m==null)
                {
//                    mcreate(0);
                    m=MediaPlayer.create(MainActivity.this,R.raw.song1);

                    title.setText(songs[0]);
                    m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            stopplayer();
                        }
                    });
                }
                m.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m!=null)
                {
                    m.pause();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m!=null)
                {
                    m.stop();
                }
            }
        });

        songlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(m!=null) {
//                    mcreate(0);
                    m.stop();
                    seekbar.setProgress(0);
                }

                title.setText(songs[i]);
//                mcreate(i);

                int resid = getResources().getIdentifier((String) songr.get(i),"raw",getPackageName());

                m=MediaPlayer.create(MainActivity.this,resid);

                // Displaying Total Duration time
//                songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(m.getDuration()));
                // Displaying time completed playing
//                songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(m.getCurrentPosition()));
                m.start();

            }
        });


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (m != null) {
                    seekbar.setMax(m.getDuration());
                    seekbar.setProgress(m.getCurrentPosition());
                }
            }
        },0,10);



        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                m.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                m.seekTo(seekBar.getProgress());
            }
        });

    }

//    public void mcreate(int i)
//    {
//        switch(i)
//        {
//            case 0:m=MediaPlayer.create(this,R.raw.song1);
//                break;
//
//            case 1:m=MediaPlayer.create(this,R.raw.song2);
//                break;
//
//            case 2:m=MediaPlayer.create(this,R.raw.song3);
//                break;
//
//            case 3:m=MediaPlayer.create(this,R.raw.song4);
//                break;
//
//            case 4:m=MediaPlayer.create(this,R.raw.song5);
//                break;
//        }
//
//
//    }

    private void stopplayer()
    {
        if(m!=null)
        {
            m.release();
            m=null;
            Toast.makeText(MainActivity.this, "Media Player Released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopplayer();
    }
}

// Displaying Total Duration time
//           songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
                   // Displaying time completed playing
//                   songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));
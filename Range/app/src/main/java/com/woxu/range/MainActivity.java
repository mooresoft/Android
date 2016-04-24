package com.woxu.range;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button buttonclear;
    private TextView textview;
    private int offset;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String)msg.obj;
            textview.append(result);
            offset=textview.getLineCount()*textview.getLineHeight();
            if(offset>textview.getHeight()){
                textview.scrollTo(0,offset-textview.getHeight());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)this.findViewById(R.id.button);
        buttonclear = (Button)this.findViewById(R.id.buttonClear);
        textview = (TextView) this.findViewById(R.id.textView1);
        textview.setMovementMethod(ScrollingMovementMethod.getInstance());
        new Thread(new myThread()).start();
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textview.append("aaaaaaaaaaaaaaaaa");
                Toast.makeText(MainActivity.this,"亲，看到我给你发的信息了没？", Toast.LENGTH_SHORT).show();
                offset=textview.getLineCount()*textview.getLineHeight();
                if(offset>textview.getHeight()){
                    textview.scrollTo(0,offset-textview.getHeight());
                }
                new Thread(new myClient()).start();
            }
        });

        buttonclear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textview.setText("");
                textview.scrollTo(0,0);
                //Toast.makeText(MainActivity.this,"清空完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class myClient implements Runnable{
        @Override
        public void run() {
            try {
                DatagramSocket datagramSocket = new DatagramSocket(4568);
                //InetAddress serverAddres = InetAddress.getByName("192.168.43.13");
                InetAddress serverAddres = InetAddress.getByName("127.0.0.1");
                String str = "Hello";
                byte [] data = str.getBytes();
                DatagramPacket packet = new DatagramPacket(data,data.length,serverAddres,4567);

                //调用socket对象的send方法，发送数据
                datagramSocket.send(packet);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return;
            }
        }
    }

    public class myThread implements Runnable{
        @Override
        public void run(){
            //创建一个DatagramSocket对象，并指定监听的端口号
            DatagramSocket datagramSocket;
            DatagramPacket packet;

            try {
                datagramSocket = new DatagramSocket(4567);
            } catch (SocketException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return;
            }
            byte data [] = new byte[1024];
            //创建一个空的DatagramPacket对象
            packet = new DatagramPacket(data,data.length);
            while (true){
                //使用receive方法接收客户端所发送的数据，
                //如果客户端没有发送数据，该进程就停滞在这里
                try {
                    datagramSocket.receive(packet);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    break;
                }

                String result = new String(packet.getData(),packet.getOffset(), packet.getLength());
                Message message = Message.obtain();
                message.obj = result;
                message.what = 1;
                handler.sendMessage(message);
            }
        }
    }
}

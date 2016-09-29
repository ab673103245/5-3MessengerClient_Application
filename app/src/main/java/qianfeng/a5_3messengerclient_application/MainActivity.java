package qianfeng.a5_3messengerclient_application;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sang.User;

public class MainActivity extends AppCompatActivity {

    private Messenger messenger;
    private Messenger messenger_get;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn);
        tv = (TextView) findViewById(R.id.tv);

        // Messenger远程服务的开始，还是要用绑定式的开启方式
        Intent intent = new Intent("my_add_service");
        ServiceConnection conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                // 绑定成功之后，就可以初始化这个信使了，其实这个信使是服务端提供的Handler的引用,其实传递的就是Handler的引用
                messenger = new Messenger(service);  // 这个service里面夹带的那个hander其实是服务端的new出来的hander的引用。！

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        boolean b = bindService(intent, conn, Service.BIND_AUTO_CREATE);
        btn.setEnabled(b);

        messenger_get = new Messenger(new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1: // 从服务端获取的消息的what为1的消息。
                        // 这个msg 是接收从服务端发送过来的消息
                        int arg1 = msg.arg1;
                        tv.setText(arg1+"");
                        break;
                }
            }
        });



    }

    public void add(View view) { // 加法

        // 在这里，客户端要向服务端发送消息了

        Message msg = Message.obtain();
        msg.what = 0; // 指定服务端接收消息的标识，即服务端的 switch(msg.what)中的case的标识
        msg.arg1 = 90;
        msg.arg2 = 30; // msg

        // msg要传送引用类型的数据，都要使引用类型的数据实现Serializable接口！  实现Parceable也是不行的
        User user = new User("zhangsan", 20, "张三");
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        msg.setData(bundle);  // 要传送非基本数据类型，要用到这个setData()里面的bundle只能传送实现了Serializable的引用类型的数据,然后用到这个Serializable接口，
        msg.replyTo = messenger_get;

        try {
            // messenger是从绑定服务成功后，onServiceConnected(IBinder service)携带的参数IBinder带过来的
            // 这个Messenger messenger = new Messenger()
            messenger.send(msg); // messenger是从bingService()绑定成功时调用的onServiceConnected(IBinder service)中携带过来的参数IBinder类型的service中来的

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}

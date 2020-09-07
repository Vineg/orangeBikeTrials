package ru.vineg.multiplayerTest;

import com.badlogic.gdx.Game;
import ru.vineg.orangeBikeFree.backend.MotodroidBackendApi;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 02.02.14
 * Time: 0:26
 * To change this template use File | Settings | File Templates.
 */
public class Test extends Game {


    @Override
    public void create() {
//        while (true) {
//            try {
//
//                String messageStr = "Hello Android!";
//                int server_port = 9876;
//                DatagramSocket s = new DatagramSocket();
//                InetAddress local = InetAddress.getByName("127.0.0.1");
//                int msg_length = messageStr.length();
//                byte[] message = messageStr.getBytes();
//                DatagramPacket p = new DatagramPacket(message, msg_length, local,
//                        server_port);
//                s.send(p);
//
//                Thread.sleep(1000);
//            } catch (Exception e) {
//
//            }
//        }

//        LevelReplay replay = new LevelReplay(new Level(new VertexMap()));
//        replay.add(new ControlsAction(1,true,1));
//        replay.add(new ControlsAction(2,false,3));
//        Json json = new Json();
//        String str = json.toJson(replay);
//        System.out.println(str);
//        System.out.println(new Json().fromJson(LevelReplay.class, str).get(0).down);

        MotodroidBackendApi.submitReplay("test");
    }
}

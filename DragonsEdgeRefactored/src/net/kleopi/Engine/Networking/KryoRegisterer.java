/**
 *
 */
package net.kleopi.Engine.Networking;

import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;

import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Engine.Instances.Character;
import net.kleopi.Engine.Instances.Circle;
import net.kleopi.Engine.Instances.Instance;
import net.kleopi.Engine.Networking.UpdateObjects.InstanceListUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.LoginUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.TileMapUpdate;

public class KryoRegisterer {

	/**
	 *
	 */
	public static void registerClasses(Kryo kryo) {
		kryo.register(LoginUpdate.class);
		kryo.register(TileMapUpdate.class);
		kryo.register(char[][].class);
		kryo.register(char[].class);
		kryo.register(ArrayList.class);
		kryo.register(Instance.class);
		kryo.register(InstanceListUpdate.class);
		kryo.register(Character.class);
		kryo.register(Circle.class);
		kryo.register(Sprite.class);

	}

}

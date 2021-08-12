/*
 	*Arquivo java que implementa uma lampada seguindo o framework WebThings.
 	*Este c�digo � p�blico e fornecido pelo site WebThings, dispon�vel em <https://webthings.io/framework/>. 
 	*Algumas modifica��os foram feitas em rela��o ao original, por�m todo o c�digo foi mantido e somente o que n�o era objeto de interesse foi colocado em forma de coment�rio.
 	*As fun��es de ligar e desligar e de seguran�a quanto a temperatura foram mantidas.
 	*As fun��es de controle de intensidade de luminosidade e de "fading" foram retiradas.
 	*Mais informa��es sobre o c�digo e sobre o framework est�o dispon�veis no github da implementa��o da biblioteca java para WebThings, dispon�vel em <https://github.com/WebThingsIO/webthing-java>
 */

package io.webthings.webthing.example;

import org.json.JSONArray;
import org.json.JSONObject;
import io.webthings.webthing.Action;
import io.webthings.webthing.Event;
import io.webthings.webthing.Property;
import io.webthings.webthing.Thing;
import io.webthings.webthing.Value;
import io.webthings.webthing.WebThingServer;
import io.webthings.webthing.errors.PropertyError;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class Lamp {
	/*
	 	* Classe Lamp implementa uma lampada utilizando as bibliotecas de WebThing 
	 	* Fonte: <https://webthings.io/framework/>
	*/
    public static Thing makeThing() {
        /*
         	* m�todo inicializa uma Thing, gerando sua Thing Description.
         	* No momento somente est�o sendo inicializadas as fun��es de ligar e desligar e de seguran�a quanto a temperatura.
        */
    	Thing thing = new Thing("urn:dev:ops:my-lamp-1234",
                                "My Lamp",
                                new JSONArray(Arrays.asList("OnOffSwitch",
                                                            "Light")),
                                "A web connected lamp");

        JSONObject onDescription = new JSONObject();
        onDescription.put("@type", "OnOffProperty");
        onDescription.put("title", "On/Off");
        onDescription.put("type", "boolean");
        onDescription.put("description", "Whether the lamp is turned on");
        thing.addProperty(new Property(thing,
                                       "on",
                                       new Value(true),
                                       onDescription));
        /*
        JSONObject brightnessDescription = new JSONObject();
        brightnessDescription.put("@type", "BrightnessProperty");
        brightnessDescription.put("title", "Brightness");
        brightnessDescription.put("type", "integer");
        brightnessDescription.put("description",
                                  "The level of light from 0-100");
        brightnessDescription.put("minimum", 0);
        brightnessDescription.put("maximum", 100);
        brightnessDescription.put("unit", "percent");
        thing.addProperty(new Property(thing,
                                       "brightness",
                                       new Value(50),
                                       brightnessDescription));
		
        JSONObject fadeMetadata = new JSONObject();
        JSONObject fadeInput = new JSONObject();
        JSONObject fadeProperties = new JSONObject();
        JSONObject fadeBrightness = new JSONObject();
        JSONObject fadeDuration = new JSONObject();
        fadeMetadata.put("title", "Fade");
        fadeMetadata.put("description", "Fade the lamp to a given level");
        fadeInput.put("type", "object");
        fadeInput.put("required",
                      new JSONArray(Arrays.asList("brightness", "duration")));
        fadeBrightness.put("type", "integer");
        fadeBrightness.put("minimum", 0);
        fadeBrightness.put("maximum", 100);
        fadeBrightness.put("unit", "percent");
        fadeDuration.put("type", "integer");
        fadeDuration.put("minimum", 1);
        fadeDuration.put("unit", "milliseconds");
        fadeProperties.put("brightness", fadeBrightness);
        fadeProperties.put("duration", fadeDuration);
        fadeInput.put("properties", fadeProperties);
        fadeMetadata.put("input", fadeInput);
        thing.addAvailableAction("fade", fadeMetadata, FadeAction.class);
		*/
        JSONObject overheatedMetadata = new JSONObject();
        overheatedMetadata.put("description",
                               "The lamp has exceeded its safe operating temperature");
        overheatedMetadata.put("type", "number");
        overheatedMetadata.put("unit", "degree celsius");
        thing.addAvailableEvent("overheated", overheatedMetadata);
		
        return thing;
    }

    public static void main(String[] args) {
        Thing thing = makeThing(); //cria o objeto Thing
        WebThingServer server; //inicializa o servidor para WebThing

        try {
            // If adding more than one thing, use MultipleThings() with a name.
            // In the single thing case, the thing's name will be broadcast.
            server = new WebThingServer(new WebThingServer.SingleThing(thing),
                                        8888);

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    server.stop();
                }
            });

            server.start(false);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public static class OverheatedEvent extends Event {
        public OverheatedEvent(Thing thing, int data) {
            super(thing, "overheated", data);
        }
    }
    /*
    public static class FadeAction extends Action {
        public FadeAction(Thing thing, JSONObject input) {
            super(UUID.randomUUID().toString(), thing, "fade", input);
        }

        @Override
        public void performAction() {
            Thing thing = this.getThing();
            JSONObject input = this.getInput();
            try {
                Thread.sleep(input.getInt("duration"));
            } catch (InterruptedException e) {
            }

            try {
                thing.setProperty("brightness", input.getInt("brightness"));
                thing.addEvent(new OverheatedEvent(thing, 102));
            } catch (PropertyError e) {
            }
        }
    }
    */
}


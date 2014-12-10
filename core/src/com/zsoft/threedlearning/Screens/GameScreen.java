package com.zsoft.threedlearning.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.zsoft.threedlearning.MainGame;

/**
 * Created by zac520 on 12/9/14.
 */
public class GameScreen implements Screen {

    MainGame mainGame;
    BitmapFont font;
    MainGame game;
    OrthographicCamera camera;
    Stage stage;
    SpriteBatch batch;
    Skin skin;
    public PerspectiveCamera cam;
    public AssetManager assets;
    public boolean loading;

    /*3d stuff*/
    public Model model;
    public ModelInstance instance;
    public ModelBatch modelBatch;
    public Environment environment;
    public CameraInputController camController;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public Array<ModelInstance> blocks = new Array<ModelInstance>();
    public Array<ModelInstance> invaders = new Array<ModelInstance>();
    public ModelInstance ship;
    public ModelInstance space;

    /*testvars*/
    public Model model2;
    public ModelInstance instance2;

    public GameScreen(MainGame pGame){
        game = pGame;
        batch = new SpriteBatch();

        //initialize camera
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(100, 100, 100);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        //initialize the batch to hold models
        modelBatch = new ModelBatch();

        //make a box model
//        ModelBuilder modelBuilder = new ModelBuilder();
//        model2 = modelBuilder.createBox(5f, 5f, 5f,
//                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//        instance2 = new ModelInstance(model2);


        //set up some lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        //set up a controller
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        //load the model(s)
        assets = new AssetManager();
        assets.load("assets/invaders/spaceinvaders.g3db", Model.class);
        loading = true;
    }

    @Override
    public void show() {

    }
    private void doneLoading() {
        Model model = assets.get("assets/invaders/spaceinvaders.g3db", Model.class);
        for (int i = 0; i < model.nodes.size; i++) {
            String id = model.nodes.get(i).id;
            ModelInstance instance = new ModelInstance(model, id);
            Node node = instance.getNode(id);

            instance.transform.set(node.globalTransform);
            node.translation.set(0,0,0);
            node.scale.set(1,1,1);
            node.rotation.idt();
            instance.calculateTransforms();

            if (id.equals("space")) {
                space = instance;
                continue;
            }

            instances.add(instance);

            if (id.equals("ship"))
                ship = instance;
            else if (id.startsWith("block"))
                blocks.add(instance);
            else if (id.startsWith("invader"))
                invaders.add(instance);
        }

        loading = false;
    }
    @Override
    public void render(float delta) {
        // clear the screen
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (loading && assets.update()){
            doneLoading();
        }


        camController.update();


        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        if (space != null)
            modelBatch.render(space);

        //test area
        //modelBatch.render(instance2, environment);

        //

        modelBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();

    }
}

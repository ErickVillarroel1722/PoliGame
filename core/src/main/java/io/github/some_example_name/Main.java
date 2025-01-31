package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private Personaje personaje;
    private Enemigo enemigo;

    // Variables para el joystick virtual
    private float joystickCenterX, joystickCenterY;
    private float joystickX, joystickY;
    private static final float JOYSTICK_RADIUS = 100f; // Tamaño del radio del joystick virtual

    @Override
    public void create() {
        batch = new SpriteBatch();

        try {
            tiledMap = new TmxMapLoader().load("mapa.tmx");
            tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        } catch (Exception e) {
            Gdx.app.error("Error", "No se pudo cargar el mapa: " + e.getMessage());
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        personaje = new Personaje("personaje.png", 100, 100);
        enemigo = new Enemigo("enemigo.png", 200, 200);

        // Posición inicial del centro del joystick
        joystickCenterX = 150;
        joystickCenterY = 150;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        // Lógica del joystick
        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            float deltaX = touchX - joystickCenterX;
            float deltaY = touchY - joystickCenterY;
            float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            // Si el toque está dentro del radio del joystick
            if (distance < JOYSTICK_RADIUS) {
                joystickX = deltaX;
                joystickY = deltaY;
            } else {
                // Limitar al radio máximo
                float ratio = JOYSTICK_RADIUS / distance;
                joystickX = deltaX * ratio;
                joystickY = deltaY * ratio;
            }

            // Mover el personaje en base al joystick
            personaje.mover(joystickX / 10, joystickY / 10); // Ajustamos la velocidad dividiendo por 10
        }

        // Salto (cuando se toca la pantalla)
        if (Gdx.input.justTouched()) {
            personaje.saltar();
        }

        // Actualizar la cámara
        camera.position.set(personaje.getX() + 16, personaje.getY() + 16, 0);
        camera.update();

        if (tiledMapRenderer != null) {
            tiledMapRenderer.setView(camera);
            tiledMapRenderer.render();
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        personaje.render(batch);
        enemigo.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (tiledMap != null) tiledMap.dispose();
        if (tiledMapRenderer != null) tiledMapRenderer.dispose();
        personaje.dispose();
        enemigo.dispose();
    }
}

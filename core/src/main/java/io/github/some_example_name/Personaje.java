package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Personaje {
    private Texture texture;
    private float x, y;
    private float velocidadY = 0; // Velocidad en el eje Y para el salto
    private boolean enElAire = false;
    private static final float GRAVEDAD = -0.5f; // Gravedad para el salto
    private static final float VELOCIDAD_SALTO = 15f; // Velocidad inicial de salto

    public Personaje(String texturePath, float x, float y) {
        try {
            this.texture = new Texture(Gdx.files.internal(texturePath));
        } catch (Exception e) {
            Gdx.app.error("Error", "No se pudo cargar la textura del personaje: " + e.getMessage());
        }
        this.x = x;
        this.y = y;
    }

    public void mover(float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
    }

    // Método para saltar
    public void saltar() {
        if (!enElAire) {
            velocidadY = VELOCIDAD_SALTO;
            enElAire = true;
        }
    }

    public void actualizar() {
        if (enElAire) {
            velocidadY += GRAVEDAD; // Aplicar gravedad
            y += velocidadY;

            // Verificar si el personaje ha tocado el suelo
            if (y <= 0) {
                y = 0;
                velocidadY = 0;
                enElAire = false;
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

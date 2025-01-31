package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemigo {
    private Texture texture;
    private float x, y;
    private Rectangle bounds; // Área de colisión

    public Enemigo(String texturePath, float x, float y) {
        try {
            this.texture = new Texture(Gdx.files.internal(texturePath));
        } catch (Exception e) {
            Gdx.app.error("Error", "No se pudo cargar la textura del enemigo: " + e.getMessage());
        }
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight()); // Definir el área de colisión
    }

    // Método para mover al enemigo de izquierda a derecha
    public void actualizar() {
        x += 2; // Mueve al enemigo a la derecha constantemente
        if (x > Gdx.graphics.getWidth()) { // Si llega al borde de la pantalla, vuelve a la izquierda
            x = 0;
        }
        bounds.setPosition(x, y); // Actualizar la posición del área de colisión
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    // Método para obtener el área de colisión del enemigo
    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}

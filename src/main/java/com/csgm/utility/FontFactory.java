package com.csgm.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Utility class for generating custom BitmapFont instances in LibGDX using
 * TrueType font files.
 * <p>
 * This class provides a method to create fonts with specified size and
 * filtering options, ensuring high-quality scaling for different screen
 * resolutions.
 * </p>
 */
public class FontFactory {

	/**
	 * Creates a custom BitmapFont from a TrueType font file.
	 *
	 * @param fontFilePath the file path to the TrueType font (.ttf) file located in
	 *                     the `assets` folder. Example: "fonts/myfont.ttf"
	 * @param fontSize     the desired font size in pixels.
	 * @return a BitmapFont instance with the specified properties.
	 * @throws IllegalArgumentException if the font file path is invalid or the font
	 *                                  cannot be loaded.
	 */
	public static BitmapFont createFont(String fontFilePath, int fontSize) {
		// Create a font generator for the specified font file
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFilePath));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		// Set font parameters
		parameter.size = fontSize; // Font size in pixels
		parameter.magFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear; // Smooth scaling
		parameter.minFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear; // Smooth scaling

		// Uncomment these lines to enable additional effects (e.g., border, shadow)
//      parameter.borderWidth = 2; // Border width in pixels
//      parameter.shadowOffsetX = 3; // Shadow offset in X direction
//      parameter.shadowOffsetY = 3; // Shadow offset in Y direction
//      parameter.shadowColor = Color.BLACK; // Shadow color

		// Generate the font with the specified parameters
		BitmapFont font = generator.generateFont(parameter);

		// Dispose the generator to free resources
		generator.dispose();

		return font;
	}
}
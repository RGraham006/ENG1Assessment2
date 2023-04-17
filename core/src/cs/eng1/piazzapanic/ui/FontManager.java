package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

/**
 * It takes a TrueTypeFont (ttf) and converts it to all the necessary BitmapFonts that are required
 * for the UI elements.
 */
public class FontManager implements Disposable {

  private final FreeTypeFontGenerator fontGenerator;
  private final FreeTypeFontGenerator fontGeneratorItalic;
  private final BitmapFont titleFont;
  private final BitmapFont headerFont;
  private final BitmapFont subHeaderFont;
  private final BitmapFont labelFont;
  private final BitmapFont labelFontItalic;

  public FontManager() {
    fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MontserratMedium.ttf"));

    FreeTypeFontParameter titleFontParameters = new FreeTypeFontParameter();
    titleFontParameters.size = 32;
    titleFont = fontGenerator.generateFont(titleFontParameters);

    FreeTypeFontParameter headerFontParameters = new FreeTypeFontParameter();
    headerFontParameters.size = 24;
    headerFont = fontGenerator.generateFont(headerFontParameters);

    FreeTypeFontParameter subHeaderFontParameters = new FreeTypeFontParameter();
    subHeaderFontParameters.size = 18;
    subHeaderFont = fontGenerator.generateFont(subHeaderFontParameters);

    FreeTypeFontParameter labelFontParameters = new FreeTypeFontParameter();
    labelFontParameters.size = 12;
    labelFont = fontGenerator.generateFont(labelFontParameters);

    fontGeneratorItalic = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MontserratMediumItalic.ttf"));

    FreeTypeFontParameter labelFontItalicParameters = new FreeTypeFontParameter();
    labelFontItalicParameters.size = 12;
    labelFontItalic = fontGeneratorItalic.generateFont(labelFontItalicParameters);
  }

  public BitmapFont getTitleFont() {
    return titleFont;
  }

  public BitmapFont getHeaderFont() {
    return headerFont;
  }

  public BitmapFont getSubHeaderFont() {
    return subHeaderFont;
  }

  public BitmapFont getLabelFont() {
    return labelFont;
  }

  public BitmapFont getLabelFontItalic() {
    return labelFontItalic;
  }

  public BitmapFont generateFont(FreeTypeFontParameter parameter) {
    return fontGenerator.generateFont(parameter);
  }

  @Override
  public void dispose() {
    fontGenerator.dispose();
  }
}

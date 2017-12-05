package dclib.graphics.shader

import com.badlogic.gdx.graphics.glutils.ShaderProgram

object MaskShader {
    private val VERT = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
            "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
            "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
            "uniform mat4 u_projTrans;\n" +
            " \n" +
            "varying vec4 vColor;\n" +
            "varying vec2 vTexCoord;\n" +
            "void main() {\n" +
            "   vColor = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
            "   vTexCoord = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
            "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
            "}"

    private val FRAG = (
            //GL ES specific stuff
            "#ifdef GL_ES\n" //
                    + "#define LOWP lowp\n" //
                    + "precision mediump float;\n" //
                    + "#else\n" //
                    + "#define LOWP \n" //
                    + "#endif\n" + //
                    "varying LOWP vec4 vColor;\n" +
                    "varying vec2 vTexCoord;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "uniform sampler2D u_texture1;\n" +
                    "uniform sampler2D u_mask;\n" +
                    "void main(void) {\n" +
                    "   //sample the colour from the first texture\n" +
                    "   vec4 texColor0 = texture2D(u_texture, vTexCoord);\n" +
                    "\n" +
                    "   //sample the colour from the second texture\n" +
                    "   vec4 texColor1 = texture2D(u_texture1, vTexCoord);\n" +
                    "\n" +
                    "   //get the mask; we will only use the alpha channel\n" +
                    "   float mask = texture2D(u_mask, vTexCoord).a;\n" +
                    "\n" +
                    "   //interpolate the colours based on the mask\n" +
                    "   gl_FragColor = vColor * mix(texColor0, texColor1, mask);\n" +
                    "}")

    fun create(): ShaderProgram {
        val shader = ShaderProgram(VERT, FRAG)
        if (!shader.isCompiled) {
            System.err.println(shader.log)
            System.exit(0)
        }
        if (shader.log.length != 0)
            println(shader.log)
        shader.begin()
        shader.setUniformi("u_texture1", 1)
        shader.setUniformi("u_mask", 2)
        shader.end()
        return shader
    }
}
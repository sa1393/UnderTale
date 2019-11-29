package undertale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader {
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public int positionAttribIndex;
	public int colorAttribIndex;
	
	public int getProgramID() {
		return programID;
	}

	public Shader(String vertexShaderFile, String fragmentShaderFile){
		vertexShaderID = loadShaderFromFile(vertexShaderFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShaderFromFile(fragmentShaderFile, GL_FRAGMENT_SHADER);
		
		programID = glCreateProgram();
		
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		
		glBindAttribLocation(programID, 0, "vertexData");
		glBindAttribLocation(programID, 1, "colorData");
		
		glLinkProgram(programID);
		glValidateProgram(programID);
		
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		
	}
	
	public static int loadShaderFromFile(String fileName, int type){
		StringBuilder shaderSource = new StringBuilder();
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = reader.readLine())!= null){
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}catch(IOException e){
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		
		int shaderID = glCreateShader(type);
		
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE){
			System.out.println(glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		
		
		return shaderID;
	}
	
	public void start(){
		glUseProgram(programID);
	}
	public void stop(){
		glUseProgram(0);
	}
	
	public void cleanUp(){
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
	
	public void setFloat(String name, float value) {
		int loc = glGetUniformLocation(programID, name);
		glUniform1f(loc, value);
	}

	public void setInteger(String name, int value) {
		int loc = glGetUniformLocation(programID, name);
		glUniform1i(loc, value);
	}

	public void setInteger(String name, boolean value) {
		int loc = glGetUniformLocation(programID, name);
		glUniform1i(loc, value ? 1 : 0);
	}

	public void setVector2f(String name, float x, float y) {
		int loc = glGetUniformLocation(programID, name);
		glUniform2f(loc, x, y);
	}

	public void setVector2f(String name, Vector2f vec) {
		int loc = glGetUniformLocation(programID, name);
		glUniform2f(loc, vec.x, vec.y);
	}

	public void setVector3f(String name, Vector3f vec) {
		int loc = glGetUniformLocation(programID, name);
		glUniform3f(loc, vec.x, vec.y, vec.z);
	}
	
	public void setSampler2d(String name, int i) {
		int loc = glGetUniformLocation(programID, name);
		glUniform1i(loc, i);
	}

	public void setVector4f(String name, Vector4f vec) {
		int loc = glGetUniformLocation(programID, name);
		glUniform4f(loc, vec.x, vec.y, vec.z, vec.w);
	}

	// Matrix를 넘겨주기 위한 배열.
	private float[] matrix4fArr = new float[16];

	public void setMatrix4f(String name, Matrix4f matrix) {
		int loc = glGetUniformLocation(programID, name);

		matrix.get(matrix4fArr);
		glUniformMatrix4fv(loc, false, matrix4fArr);

	}
	
}

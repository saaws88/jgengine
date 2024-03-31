#version 460 core

in vec3 position;
in vec3 color;
in vec2 texture;

out vec3 passColor;
out vec3 passTexure;

void main()
{
    gl_Position = vec4(position, 1.0);
    passColor =  color;
    passTexture =  texture;
}
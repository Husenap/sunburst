#version 460 core

layout(location = 0) in vec3 aPos;
layout(location = 1) in vec2 aTexCoord;

out vec3 vColor;
out vec2 vTexCoord;

uniform vec2 uOffset;

void main() {
  gl_Position = vec4(aPos.xy + uOffset, aPos.z, 1.0);
  vTexCoord = aTexCoord;
}
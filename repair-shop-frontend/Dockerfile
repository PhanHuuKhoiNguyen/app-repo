# ---------- Stage 1: Build ----------
FROM node:22-alpine AS builder
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .

# Nhận địa chỉ API từ bên ngoài lúc build
ARG VITE_API_URL
ARG VITE_WS_URL
ENV VITE_API_URL=http://192.168.190.133:30071/api \
     VITE_WS_URL=http://192.168.190.133:30071/ws

RUN npm run build

# ---------- Stage 2: Serve ----------
FROM node:22-alpine AS production
WORKDIR /app

RUN npm install -g serve@14

COPY --from=builder --chown=node:node /app/dist ./dist
USER node

EXPOSE 3000
CMD ["serve", "-s", "dist", "-l", "3000"]
# anonimizador-pdf

Aplicativo desktop para anonimização de documentos PDF brasileiros (CPF, CNPJ, RG, endereços, etc.).  
Roda 100% localmente — nenhum dado é enviado à internet.

---

## 🖥️ Aplicativo Desktop (modo recomendado)

### Pré-requisitos

- **Java 21+** instalado e no PATH
- **Node.js 18+** e npm instalados
- **Maven** (ou use o `./mvnw` incluído no projeto)

### Build e execução em um comando

```bash
./build-desktop.sh
```

Esse script:
1. Compila o backend Java como JAR executável (`target/*-runner.jar`)
2. Instala as dependências do Electron (`desktop/node_modules`)
3. Gera o instalador do app em `desktop/dist/`

> No **Windows**, execute via Git Bash ou WSL. Como alternativa, siga os passos manuais abaixo.

### Passos manuais

```bash
# 1. Compilar o backend
./mvnw package -DskipTests -Dquarkus.package.jar.type=uber-jar

# 2. Entrar na pasta do app desktop
cd desktop

# 3. Instalar dependências do Electron (só na primeira vez)
npm install

# 4a. Rodar em modo desenvolvimento (sem gerar instalador)
npm start

# 4b. Gerar instalador para distribuição
npm run dist          # detecta o SO automaticamente
npm run dist:win      # Windows (.exe)
npm run dist:mac      # macOS (.dmg)
npm run dist:linux    # Linux (.AppImage / .deb)
```

O instalador gerado fica em `desktop/dist/`.

---

## 🔌 API REST (modo servidor)

O backend também pode rodar sozinho, como uma API HTTP feita em Quarkus.

```bash
./mvnw quarkus:dev                          # modo desenvolvimento, em http://localhost:8080
./mvnw package                              # gera target/quarkus-app/
java -jar target/quarkus-app/quarkus-run.jar
```

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/anonimizador/redact` | Recebe o PDF no corpo (`application/pdf`) e devolve o PDF anonimizado |

Exemplo:

```bash
curl -X POST http://localhost:8080/anonimizador/redact \
  -H "Content-Type: application/pdf" \
  --data-binary @documento.pdf -o documento-anonimizado.pdf
```

A documentação interativa fica no Swagger UI, em `/q/swagger-ui`.

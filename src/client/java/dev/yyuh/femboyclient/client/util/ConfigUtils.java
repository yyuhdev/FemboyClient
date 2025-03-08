package dev.yyuh.femboyclient.client.util;

import net.fabricmc.loader.api.FabricLoader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class ConfigUtils {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("femboy_client.json");

    public static boolean get(String path) {
        if (!CONFIG_PATH.toFile().exists()) {
            return false;
        }

        try {
            File config = new File(CONFIG_PATH.toUri());
            String c = new String(Files.readAllBytes(Path.of(config.getPath())));
            JSONObject object = new JSONObject(c);

            if (!object.has(path)) return false;

            return object.getBoolean(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(String path, double integer) {
        CompletableFuture.runAsync(() -> {
            File config = new File(CONFIG_PATH.toUri());
            String c = null;

            try {
                JSONObject object = new JSONObject();
                if (config.exists()) {
                    c = new String(Files.readAllBytes(Path.of(config.getPath())));
                    object = new JSONObject(c);
                }

                object.put(path, integer);

                if (!config.exists()) {
                    config.createNewFile();
                }
                FileWriter writer = new FileWriter(config.getPath());
                writer.write(object.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public static void save(String path, int integer) {
        CompletableFuture.runAsync(() -> {
            File config = new File(CONFIG_PATH.toUri());
            String c = null;

            try {
                JSONObject object = new JSONObject();
                if (config.exists()) {
                    c = new String(Files.readAllBytes(Path.of(config.getPath())));
                    object = new JSONObject(c);
                }

                object.put(path, integer);

                if (!config.exists()) {
                    config.createNewFile();
                }
                FileWriter writer = new FileWriter(config.getPath());
                writer.write(object.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public static void save(String path, boolean state) {
        CompletableFuture.runAsync(() -> {
            File config = new File(CONFIG_PATH.toUri());
            String c = null;

            try {
                JSONObject object = new JSONObject();
                if (config.exists()) {
                    c = new String(Files.readAllBytes(Path.of(config.getPath())));
                    object = new JSONObject(c);
                }

                object.put(path, state);

                if (!config.exists()) {
                    config.createNewFile();
                }
                FileWriter writer = new FileWriter(config.getPath());
                writer.write(object.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public static void savePosition(String path, int x, int y) {
        CompletableFuture.runAsync(() -> {
            File config = new File(CONFIG_PATH.toUri());
            String c = null;

            try {
                JSONObject object = new JSONObject();
                if (config.exists()) {
                    c = new String(Files.readAllBytes(Path.of(config.getPath())));
                    object = new JSONObject(c);
                }

                JSONObject positionObject = object.optJSONObject(path);
                if (positionObject == null) {
                    positionObject = new JSONObject();
                }

                positionObject.put("x", x);
                positionObject.put("y", y);
                object.put(path, positionObject);

                if (!config.exists()) {
                    config.createNewFile();
                }
                FileWriter writer = new FileWriter(config.getPath());
                writer.write(object.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public static void saveModuleStateAndPosition(String path, boolean state, int x, int y) {
        CompletableFuture.runAsync(() -> {
            File config = new File(CONFIG_PATH.toUri());
            String c = null;

            try {
                JSONObject object = new JSONObject();
                if (config.exists()) {
                    c = new String(Files.readAllBytes(Path.of(config.getPath())));
                    object = new JSONObject(c);
                }

                object.put(path, state);

                JSONObject positionObject = object.optJSONObject(path + ".position");
                if (positionObject == null) {
                    positionObject = new JSONObject();
                }

                positionObject.put("x", x);
                positionObject.put("y", y);
                object.put(path + ".position", positionObject);

                if (!config.exists()) {
                    config.createNewFile();
                }
                FileWriter writer = new FileWriter(config.getPath());
                writer.write(object.toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public static HudPosition getPosition(String path) {
        if (!CONFIG_PATH.toFile().exists()) {
            return new HudPosition(50, 0);
        }

        try {
            File config = new File(CONFIG_PATH.toUri());
            String c = new String(Files.readAllBytes(Path.of(config.getPath())));
            JSONObject object = new JSONObject(c);

            if (!object.has(path + ".position")) return new HudPosition(50, 0);

            JSONObject positionObjects = object.getJSONObject(path + ".position");

            if (!positionObjects.has("x") || !positionObjects.has("y")) {
                return new HudPosition(50, 0);
            }

            int x = positionObjects.getInt("x");
            int y = positionObjects.getInt("y");

            return new HudPosition(x, y);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double getDouble(String path, double defaultValue) {
        if (!CONFIG_PATH.toFile().exists()) {
            return defaultValue;
        }

        try {
            File config = new File(CONFIG_PATH.toUri());
            String content = new String(Files.readAllBytes(config.toPath()));
            JSONObject object = new JSONObject(content);
            return object.optDouble(path, defaultValue);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }


    public static int getInt(String path, int defaultValue) {
        if (!CONFIG_PATH.toFile().exists()) {
            return defaultValue;
        }

        try {
            File config = new File(CONFIG_PATH.toUri());
            String content = new String(Files.readAllBytes(config.toPath()));
            JSONObject object = new JSONObject(content);
            return object.optInt(path, defaultValue);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }


    public static class HudPosition {
        private final int x;
        private final int y;

        public HudPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }
    }
}
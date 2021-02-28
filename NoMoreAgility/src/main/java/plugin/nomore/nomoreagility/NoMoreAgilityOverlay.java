package plugin.nomore.nomoreagility;

import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;

public class NoMoreAgilityOverlay extends Overlay {

    private final NoMoreAgilityPlugin plugin;
    private final NoMoreAgilityConfig config;
    private final Client client;

    @Inject
    public NoMoreAgilityOverlay(NoMoreAgilityPlugin plugin, NoMoreAgilityConfig config, Client client)
    {
        this.plugin = plugin;
        this.config = config;
        this.client = client;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.LOW);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        LocalPoint playerLocation = client.getLocalPlayer().getLocalLocation();

        Point mousePosition = client.getMouseCanvasPosition();
        plugin.getObstacles().forEach((object, obstacle) ->
        {
            Tile tile = obstacle.getTile();

            if (tile.getPlane() == client.getPlane())
            {
                Shape objectClickBox = object.getClickbox();
                if (objectClickBox != null) {


                    // GNOME
                    // 1 - Log balance 2474,3436
                    if (isPlayerWithinArea(graphics, 2471,3440,2489,3436, 0)
                            && object.getId() == 23145) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 2 - Obstacle net
                    if (isPlayerWithinArea(graphics, 2471, 3429,2477,3426, 0)
                            && object.getId() == 23134) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Tree branch
                    if (isPlayerWithinArea(graphics, 2471,3424,2476,3422, 1)
                            && object.getId() == 23559) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Balancing rope
                    if (isPlayerWithinArea(graphics, 2472, 3421, 2477, 3418, 2)
                            && object.getId() == 23557) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Tree branch
                    if (isPlayerWithinArea(graphics, 2483, 3421, 2488, 3418,2)
                            && object.getId() == 23561) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Obstacle net
                    if (isPlayerWithinArea(graphics, 2482,3425,2489,3417,0)
                            && object.getId() == 23135) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 7 - Obstacle pipe
                    if (isPlayerWithinArea(graphics, 2483,3431,2488,3427,0)
                            && object.getId() == 23138) {
                            renderStyleChoice(graphics, object.getClickbox());
                    }


                    // DRAYNOR VILLAGE
                    // 1 - Rough wall
                    if (object.getId() == 11404) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 2 - Tight rope
                    if (isPlayerWithinArea(graphics, 3097,3281,3102,3277,3)
                            && object.getId() == 11405) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Tight rope
                    if (isPlayerWithinArea(graphics, 3088,3276,3092,3272,3)
                            && object.getId() == 11406) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Narrow wall
                    if (isPlayerWithinArea(graphics, 3089,3268,3094,3265,3)
                            && object.getId() == 11430) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Wall
                    if (isPlayerWithinArea(graphics, 3087,3261,3088,3256,3)
                            && object.getId() == 11630) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Gap
                    if (isPlayerWithinArea(graphics, 3087,3255,3094,3255,3)
                            && object.getId() == 11631) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 7 - Crate
                    if (isPlayerWithinArea(graphics, 3096,3261,3101,3256,3)
                            && object.getId() == 11632) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }


                    // AL KHARID
                    // 1 - Rough wall
                    if (object.getId() == 11633) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 2 - Tightrope
                    if (isPlayerWithinArea(graphics, 3271,3192,3278,3180,3)
                            && object.getId() == 14398) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Cable
                    if (isPlayerWithinArea(graphics, 3265,3173,3272,3161,3)
                            && object.getId() == 14402) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Zip line
                    if (isPlayerWithinArea(graphics, 3283,3176,3302,3160,3)
                            && object.getId() == 14403) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Tropical tree
                    if (isPlayerWithinArea(graphics, 3313,3165,3318,3160,1)
                            && object.getId() == 14404) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Rooftop beams
                    if (isPlayerWithinArea(graphics, 3312,3179,3318,3173,2)
                            && object.getId() == 11634) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 7 - Tightrope
                    if (isPlayerWithinArea(graphics, 3312,3186,3318,3180,3)
                            && object.getId() == 14409) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 8 - Gap
                    if (isPlayerWithinArea(graphics, 3297,3194,3306,3185,3)
                            && object.getId() == 14399) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }


                    // VARROCK
                    // 1 - Rough wall 14412
                    if (object.getId() == 14412) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 2 - Clothes line 14413
                    if (isPlayerWithinArea(graphics, 3214,3419,3220,3410,3)
                            && object.getId() == 14413) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Gap 14414
                    if (isPlayerWithinArea(graphics, 3201,3417,3208,3414,3)
                            && object.getId() == 14414) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Wall 14832
                    if (isPlayerWithinArea(graphics, 3193,3416,3197,3416,1)
                            && object.getId() == 14832) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Gap 14833
                    if (isPlayerWithinArea(graphics, 3192,3406,3198,3402,3)
                            && object.getId() == 14833) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Gap 14834
                    if (isPlayerWithinArea(graphics, 3191,3404,3208,3395,3)
                            && object.getId() == 14834) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 7 - Gap 14835
                    if (isPlayerWithinArea(graphics, 3218,3404,3232,3393,3)
                            && object.getId() == 14835) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 8 - Ledge 14836
                    if (isPlayerWithinArea(graphics, 3236,3409,3240,3403,3)
                            && object.getId() == 14836) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 9 - Edge 14841
                    if (isPlayerWithinArea(graphics, 3236,3415,3240,3410,3)
                            && object.getId() == 14841) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }


                    // CANAFIS
                    // 1 - Tall tree 14843
                    if (object.getId() == 14843) {

                        LocalPoint localPoint = LocalPoint.fromWorld(client, 3508, 3489);
                        if (localPoint == null) {

                        }

                        Polygon objTile = Perspective.getCanvasTilePoly(client, localPoint);

                        Shape shape = object.getClickbox();
                        if (shape == null) {

                        }
                        switch (config.obstacleRenderStyle()) {
                            case CLICKBOX:
                                renderClickBox(graphics, shape);
                                break;
                            case FILL:
                                renderFilledClickBox(graphics, shape);
                                break;
                            case BOX:
                                Rectangle bounds = objTile.getBounds();
                                int x = (int) bounds.getCenterX() - config.obstacleBoxSize() / 2;
                                int y = (int) bounds.getCenterY() - config.obstacleBoxSize() / 2;
                                graphics.setColor(config.obstacleColor());
                                graphics.fillRect(x, y, config.obstacleBoxSize(), config.obstacleBoxSize());
                                break;
                        }


                    }
                    // 2 - Gap 14844
                    if (isPlayerWithinArea(graphics, 3504,3498,3508,3491,2)
                            && object.getId() == 14844) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Gap 14845
                    if (isPlayerWithinArea(graphics, 3496,3507,3504,3503,2)
                            && object.getId() == 14845) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Gap 14848
                    if (isPlayerWithinArea(graphics, 3485,3505,3493,3498,2)
                            && object.getId() == 14848) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Gap 14846
                    if (isPlayerWithinArea(graphics, 3474,3499,3480,3491,3)
                            && object.getId() == 14846) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Pole-vault 14894
                    if (isPlayerWithinArea(graphics, 3477,3487,3484,3481,2)
                            && object.getId() == 14894) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 7 - Gap 14847
                    if (isPlayerWithinArea(graphics, 3488,3479,3504,3468,3)
                            && object.getId() == 14847) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 8 - Gap 14897
                    if (isPlayerWithinArea(graphics, 3508,3483,3517,3474,2)
                            && object.getId() == 14897) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }


                    // FALADOR
                    // 1 - Rough wall 14898
                    if (object.getId() == 14898) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 2 - Tightrope 14899
                    if (isPlayerWithinArea(graphics, 3036,3343,3040,3342,3)
                            && object.getId() == 14899) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Hand holds 14901
                    if (isPlayerWithinArea(graphics, 3044,3349,3051,3341,3)
                            && object.getId() == 14901) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Gap 14903
                    if (isPlayerWithinArea(graphics, 3048,3358,3050,3357,3)
                            && object.getId() == 14903) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Gap 14904
                    if (isPlayerWithinArea(graphics, 3045,3367,3048,3361,3)
                            && object.getId() == 14904) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Tightrope 14905
                    if (isPlayerWithinArea(graphics, 3034,3364,3041,3361,3)
                            && object.getId() == 14905) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 7 - Tightrope 14911
                    if (isPlayerWithinArea(graphics, 3026,3355,3029,3352,3)
                            && object.getId() == 14911) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 8 - Gap 14919
                    if (isPlayerWithinArea(graphics, 3009,3358,3021,3353,3)
                            && object.getId() == 14919) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 9 - Ledge 14920
                    if (isPlayerWithinArea(graphics, 3016,3349,3022,3343,3)
                            && object.getId() == 14920) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 10 - Ledge 14921
                    if (isPlayerWithinArea(graphics, 3011,3346,3015,3344,3)
                            && object.getId() == 14921) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 11a - Ledge 14922
                    if (isPlayerWithinArea(graphics, 3009,3342,3013,3335,3)
                            && object.getId() == 14922) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 11b - Ledge 14923
                    if (isPlayerWithinArea(graphics, 3009,3342,3013,3335,3)
                            && object.getId() == 14923) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 12 - Ledge 14924
                    if (isPlayerWithinArea(graphics, 3012,3334,3018,3331,3)
                            && object.getId() == 14924) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 13 - Edge 14925
                    if (isPlayerWithinArea(graphics, 3019,3335,3024,3332,3)
                            && object.getId() == 14925) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }


                    // CAMELOT
                    // 1 - Wall 14927
                    if (object.getId() == 14927) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 2 - Gap 14928
                    if (isPlayerWithinArea(graphics, 2721,3497,2730,3490,3)
                            && object.getId() == 14928) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Tightrope 14932
                    if (isPlayerWithinArea(graphics, 2704,3497,2714,3487,2)
                            && object.getId() == 14932) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Gap 14929
                    if (isPlayerWithinArea(graphics, 2709,3482,2716,3476,2)
                            && object.getId() == 14929) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Gap 14930
                    if (isPlayerWithinArea(graphics, 2700,3475,2715,3470,3)
                            && object.getId() == 14930) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Edge 14931
                    if (isPlayerWithinArea(graphics, 2690,3466,2703,3459, 2)
                            && object.getId() == 14931) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }


                    // POLLI
                    // 1 - Basket 14935
                    if (object.getId() == 14935) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 2 - Market stall 14936
                    if (isPlayerWithinArea(graphics, 3346,2968,3351,2963,1)
                            && object.getId() == 14936) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 3 - Banner 14937
                    if (isPlayerWithinArea(graphics, 3352,2976,3355,2973,1)
                            && object.getId() == 14937) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 4 - Gap 14938
                    if (isPlayerWithinArea(graphics, 3360,2979,3362,2977,1)
                            && object.getId() == 14938) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 5 - Tree 14939
                    if (isPlayerWithinArea(graphics, 3366,2976,3370,2974,1)
                            && object.getId() == 14939) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 6 - Rough wall 14940
                    if (isPlayerWithinArea(graphics, 3365,2986,3369,2982,1)
                            && object.getId() == 14940) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 7 - Monkeybars 14941
                    if (isPlayerWithinArea(graphics, 3355,2985,3365,2980,2)
                            && object.getId() == 14941) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 8 - Tree 14944
                    if (isPlayerWithinArea(graphics, 3357,2995,3370,2990,2)
                            && object.getId() == 14944) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }
                    // 9 - Drying line 14945
                    if (isPlayerWithinArea(graphics, 3356,3004,3364,3000,2)
                            && object.getId() == 14945) {
                        renderStyleChoice(graphics, object.getClickbox());

                    }


                }
            }

        });

        return null;
    }

    public void renderStyleChoice(Graphics2D graphics, Shape shapeClickBox) {

        switch (config.obstacleRenderStyle()) {
            case CLICKBOX:
                renderClickBox(graphics, shapeClickBox);
                break;
            case FILL:
                renderFilledClickBox(graphics, shapeClickBox);
                break;
            case BOX:
                renderBox(graphics, shapeClickBox);
                break;
        }

    }

    public void renderFilledClickBox(Graphics2D graphics, Shape shapeClickBox) {

        if (shapeClickBox != null) {
            OverlayUtil.renderHoverableArea(graphics, shapeClickBox, client.getMouseCanvasPosition(), config.obstacleColor(), null, null);
        }

    }

    public void renderBox(Graphics2D graphics, Shape shapeClickBox) {

        if (shapeClickBox != null) {
            int x = (int) shapeClickBox.getBounds().getCenterX() - config.obstacleBoxSize() / 2;
            int y = (int) shapeClickBox.getBounds().getCenterY() - config.obstacleBoxSize() / 2;
            graphics.setColor(config.obstacleColor());
            graphics.fillRect(x, y, config.obstacleBoxSize(), config.obstacleBoxSize());
        }

    }

    public void renderClickBox(Graphics2D graphics, Shape shapeClickBox) {

        if (shapeClickBox != null) {
            OverlayUtil.renderPolygon(graphics, shapeClickBox, config.obstacleColor());
        }

    }

    public boolean isPlayerWithinArea(Graphics2D graphics, int x1, int y2, int x2, int y1, int z) {

        Player player = client.getLocalPlayer();

        if (player == null) {
            return false;
        }

        for (Tile markOfGraceTile : plugin.getMarksOfGrace())
        {
            renderMarkOfGrace(graphics, markOfGraceTile);
        }

        WorldPoint playerLocation = player.getWorldLocation();

        int pX = playerLocation.getX();
        int pY = playerLocation.getY();
        int pZ = playerLocation.getPlane();
        return pX >= x1 && pX <= x2 && pY >= y1 && pY <= y2 && pZ == z;

    }

    public void renderMarkOfGrace(Graphics2D graphics, Tile tile) {

        if (tile.getWorldLocation().distanceTo(client.getLocalPlayer().getWorldLocation()) > config.markDistance()) {
            return;
        }

        if (tile.getPlane() == client.getPlane()) {

            final Polygon poly = Perspective.getCanvasTilePoly(client, tile.getLocalLocation());

            if (poly != null) {
                switch (config.marksRenderStyle()) {
                    case OFF:
                        break;
                    case CLICKBOX:
                        OverlayUtil.renderHoverableArea(graphics, poly, client.getMouseCanvasPosition(), config.marksColor(), null, null);
                        break;
                    case FILL:
                        OverlayUtil.renderPolygon(graphics, poly, config.marksColor());
                        break;
                    case BOX:
                        int x = (int) poly.getBounds().getCenterX() - config.marksBoxSize() / 2;
                        int y = (int) poly.getBounds().getCenterY() - config.marksBoxSize() / 2;
                        graphics.setColor(config.marksColor());
                        graphics.fillRect(x, y, config.marksBoxSize(), config.marksBoxSize());
                        break;
                }
            }
        }

    }

}

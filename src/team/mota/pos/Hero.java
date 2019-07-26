package team.mota.pos;

import team.mota.panel.MotaMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jun-wqh seeyul
 */
public class Hero extends Position {

    public Map<String, Integer> article = new HashMap<>();
    Integer[][] maps;
    public String msg = null;

    public Hero(Integer x, Integer y, Integer map) {
        this.x = x;
        this.y = y;
        maps = MotaMap.motemap.get(map);
        article.put("level", 1);
        article.put("atk", 10);
        article.put("dct", 10);
        article.put("money", 0);
        article.put("hp", 600);
        article.put("redKey", 1);
        article.put("blueKey", 1);
        article.put("yellowKey", 1);
    }

    public boolean add(String name, Integer count) {
        article.put(name, article.get(name) + count);
        return true;
    }


    public boolean use(String name, Integer count) {
        if (article.get(name) >= count) {
            article.put(name, article.get(name) - count);
            return true;
        } else {
            msg = "道具不足";
        }
        return false;
    }

    public boolean buy(String name, Integer count, Integer money) {
        if (article.get("money") >= money) {
            article.put(name, article.get(name) + count);
            return true;
        }
        return false;
    }

    public Boolean atk(Monster monster) {
        int hp = monster.hp;
        if (article.get("atk") <= monster.dct) {
            return false;
        }
        if (article.get("dct") > monster.atk) {
            return true;
        }
        for (; ; ) {
            hp -= article.get("atk") - monster.dct;
            if (hp <= 0) {
                return true;
            }
            article.put("hp", article.get("hp") + article.get("dct") - monster.atk);
            if (article.get("hp") <= 0) {
                return false;
            }
        }
    }

    public Boolean checkatk(Monster monster) {
        Integer ahp = article.get("hp");
        Integer bhp = monster.hp;
        for (; ; ) {
            bhp -= article.get("atk") - monster.dct;
            if (bhp <= 0) {
                return true;
            }
            ahp -= monster.atk - article.get("dct");
            if (ahp <= 0) {
                return false;
            }
        }
    }

    public boolean move(int x, int y) {
        int rx = this.x + x;
        int ry = this.y + y;
        Boolean result = false;
        boolean level = false;
        if (rx >= 0 && rx < 11 && ry >= 0 && ry < 11) {
            // 事件处理
            Integer even = maps[rx][ry];
            switch (even) {
                // 道具
                case MotaMap.R:
                case MotaMap.B:
                case MotaMap.Y:
                case MotaMap.U:
                case MotaMap.V:
                case MotaMap.W:
                case MotaMap.J:
                case MotaMap.K:
                case MotaMap.A:
                    Article article = MonstrtMap.articleMap.get(even);
                    result = this.add(article.name, article.value);
                    break;
                // 打怪
                case MotaMap.a:
                case MotaMap.b:
                case MotaMap.c:
                case MotaMap.d:
                case MotaMap.e:
                case MotaMap.f:
                case MotaMap.g:
                case MotaMap.h:
                case MotaMap.i:
                case MotaMap.j:
                    Monster monster = MonstrtMap.monsterMap.get(even);
                    Boolean checkatk = this.checkatk(monster);
                    if (checkatk) {
                        result = this.atk(monster);
                        if (result) {
                            this.add("money", monster.money);
                        }
                    } else {
                        msg = "打不过";
                    }
                    break;
                case MotaMap.Q:
                    break;
                case MotaMap.L:
                case MotaMap.O:
                    result = true;
                    break;
                //开门
                case MotaMap.D:
                    result = this.use("yellowKey", 1);
                    break;
                case MotaMap.E:
                    result = this.use("blueKey", 1);
                    break;
                case MotaMap.F:
                    result = this.use("redKey", 1);
                    break;
                //上楼
                case MotaMap.T:
                    this.article.put("level", this.article.get("level") + 1);
                    // 位置
                    maps = MotaMap.motemap.get(this.article.get("level"));
                    boolean flagt = false;
                    for (int i = 0; i < maps.length; i++) {
                        if (flagt) {
                            break;
                        }
                        for (int j = 0; j < maps[i].length; j++) {
                            if (maps[i][j] == MotaMap.H) {
                                this.x = i;
                                this.y = j;
                                flagt = true;
                                break;
                            }
                        }
                    }
                    level = true;
                    result = true;
                    break;
                //下楼
                case MotaMap.S:
                    this.article.put("level", this.article.get("level") - 1);
                    // 位置
                    maps = MotaMap.motemap.get(this.article.get("level"));
                    boolean flags = false;
                    for (int i = 0; i < maps.length; i++) {
                        if (flags) {
                            break;
                        }
                        for (int j = 0; j < maps[i].length; j++) {
                            if (maps[i][j] == MotaMap.H) {
                                this.x = i;
                                this.y = j;
                                flags = true;
                                break;
                            }
                        }
                    }
                    level = true;
                    result = true;
                    break;
                default:
                    break;
            }
            if (result && !level) {
                maps[this.x][this.y] = MotaMap.L;
                maps[rx][ry] = 100;
                this.x = rx;
                this.y = ry;
            }
        }
        return result;
    }

    public boolean up() {
        return move(-1, 0);
    }

    public boolean down() {
        return move(1, 0);
    }

    public boolean left() {
        return move(0, -1);
    }

    public boolean right() {
        return move(0, 1);
    }

    // private Integer exp;
    // private Integer level;

}





package cn.qqtextwar.entity.tasks;

import cn.qqtextwar.entity.player.Item;

import java.util.Map;

public class Task {

    private Award award;

    public Task(Award award) {
        this.award = award;
    }

    public Award getAward() {
        return award;
    }

    public class Award{
        private int money;
        private int exp;
        private Map<Item,Integer> items;
        private int likability;

        public Award(int money, int exp, Map<Item, Integer> items, int likability) {
            this.money = money;
            this.exp = exp;
            this.items = items;
            this.likability = likability;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public Map<Item, Integer> getItems() {
            return items;
        }

        public void setItems(Map<Item, Integer> items) {
            this.items = items;
        }

        public int getLikability() {
            return likability;
        }

        public void setLikability(int likability) {
            this.likability = likability;
        }
    }
}

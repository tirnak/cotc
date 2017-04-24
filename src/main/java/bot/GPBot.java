package bot;

import java.util.List;

class GPBot extends TemplateBot {
    public static void main(String args[]) throws IllegalAccessException, InstantiationException {
        TemplateBot bot = new GPBot();
        bot.run();
    }
    @Override
    public void printForShip(Ship myI, List<Barrel> bs, List<Ship> my, List<Ship> enemy, List<Mine> ms, List<Cannonball> cs) {
        //## ##//
    }
}

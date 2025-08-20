package org.uidemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.uidemo.callback.BattleResult;
import org.uidemo.callback.RoundResultHandler;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Battle {
    private final RoundResultHandler roundResultHandler;
    private final Role host, guest;
    private double initHostHealth,initGuestHealth;
    private boolean finished = false;

    public String battle() {
        initHostHealth = host.getHp();
        initGuestHealth = guest.getHp();
        while (host.getHp() > 0 && guest.getHp() > 0) {
            BattleResult.BattleResultBuilder builder = BattleResult.builder().host(host).guest(guest);
            builder.hostOldHP(host.getHp()).guestOldHP(guest.getHp());
            builder.initGuestHealth(initGuestHealth).initHostHealth(initHostHealth);
            double hostNewHp = host.getHp() - guest.getAtk() / (guest.getAtk() + host.getDef());
            double guestNewHp = guest.getHp() - host.getAtk() / (host.getAtk() + guest.getDef());
            builder.hostNewHP(hostNewHp).guestNewHP(guestNewHp);
            host.setHp(hostNewHp);
            guest.setHp(guestNewHp);
            if (roundResultHandler != null)
                roundResultHandler.onResultReceived(builder.build());
        }

        if (host.getHp() <= 0 && guest.getHp() <= 0) {
            return "平局";
        } else if (host.getHp() > 0 && guest.getHp() <= 0) {
            return (host.getName() + "获胜");
        } else if (host.getHp() <= 0 && guest.getHp() > 0) {
            return (guest.getName() + "获胜");
        } else throw new IllegalStateException("战斗出现异常");
    }
}

package org.uidemo;

import lombok.*;
import org.uidemo.callback.BattleResult;
import org.uidemo.callback.RoundResultHandler;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class Battle {
    private final RoundResultHandler roundResultHandler;
    @NonNull
    private final Role host, guest;
    private Double initHostHealth, initGuestHealth;
    private boolean finished = false;

    public String battle() {
        initHostHealth = initHostHealth != null ? initHostHealth : host.getHp();
        initGuestHealth = initGuestHealth != null ? initGuestHealth : guest.getHp();
        host.setHp(initHostHealth);
        guest.setHp(initGuestHealth);
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

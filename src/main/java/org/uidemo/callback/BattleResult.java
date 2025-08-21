package org.uidemo.callback;

import lombok.Builder;
import org.uidemo.Role;

@Builder
public record BattleResult(
        Role host, Role guest,
        double initHostHealth, double initGuestHealth,
        double hostOldHP, double hostNewHP,
        double guestOldHP, double guestNewHP) {
}

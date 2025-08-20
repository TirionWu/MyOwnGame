package org.uidemo.callback;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.uidemo.Role;

@Builder
@RequiredArgsConstructor
@Data
public class BattleResult {
    private final Role host, guest;
    private final double initHostHealth,initGuestHealth;
    private final double hostOldHP, hostNewHP;
    private final double guestOldHP, guestNewHP;
}

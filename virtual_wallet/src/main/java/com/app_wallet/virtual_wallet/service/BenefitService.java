package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.entity.Benefit;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import com.app_wallet.virtual_wallet.utils.Node;

import java.util.Iterator;

public class BenefitService {
    private final LinkedList<BenefitDTO> benefits = new LinkedList<BenefitDTO>();

    public void createBenefit(BenefitDTO benefit) {
        benefits.add(benefit);
    }

    public void saveBenefit(BenefitDTO benefit) {
        if (getBenefitById(benefit.getId()) != null) {
            updateBenefit(benefit);
        } else {
            createBenefit(benefit);
        }
    }

    public BenefitDTO updateBenefit(BenefitDTO benefit) {
        for (int i = 0; i < benefits.size(); i++) {
            BenefitDTO ben = benefits.get(i);
            if (ben.getId().equals(benefit.getId())) {
                benefits.remove(i);      // Elimina la cuenta antigua
                benefits.add(benefit);   // Agrega la cuenta nueva
                return benefit;          // Devuelve la cuenta actualizada
            }
        }
        return null;
    }

    public void deleteBenefit(BenefitDTO benefit) {
        benefits.removeIf(b -> b.getId().equals(benefit.getId()));
    }

    public BenefitDTO getBenefitById(Long id) {
        Iterator<AccountDTO> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            AccountDTO acc = iterator.next();
            if (acc.getId().equals(id)) {
                iterator.remove();  // Elimina el elemento actual
            }
        }
    }

    public BenefitDTO getBenefitById(Long id) {
        Node<BenefitDTO> current = benefits.getHead();
        while (current != null) {
            BenefitDTO benefit = current.getData();
            if (benefit.getID().equals(id)) {
                return benefit;  // Se encontró: devuelve el objeto
            }
            current = current.getNext();  // Avanza al siguiente nodo
        }
        return null;          // No se encontró ninguna coincidencia
    }
}

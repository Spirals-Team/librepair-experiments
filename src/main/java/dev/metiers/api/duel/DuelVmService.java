package dev.metiers.api.duel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.api.viewModels.duel.DuelReturnVm;
import dev.controller.api.viewModels.duel.DuelVm;
import dev.entites.Duel;
import dev.metiers.DuelService;

@Service
public class DuelVmService {
	private DuelService duelService;

	public DuelVmService(DuelService duelService) {
		super();
		this.duelService = duelService;
	}

	@Transactional
	public List<DuelVm> listAllDuel() {
		return duelService.lister().stream().map(DuelVm::new).collect(Collectors.toList());
	}

	@Transactional
	public DuelReturnVm createDuelReturnVm(Duel duel) {
		return new DuelReturnVm(duel);
	}

	@Transactional
	public DuelVm createDuelVm(Duel duel) {
		return new DuelVm(duel);
	}

	@Transactional
	public DuelVm findById(Long id) {
		return createDuelVm(duelService.findDuelById(id));
	}

}

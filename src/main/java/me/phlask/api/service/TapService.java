package me.phlask.api.service;

import me.phlask.api.dto.Result;
import me.phlask.api.dto.params.LocationParams;
import me.phlask.api.dto.response.TapResponse;


public class TapService {
    public Result<TapResponse> getRequest(LocationParams parmas) {
        return Result.of(new TapResponse());
    }
}

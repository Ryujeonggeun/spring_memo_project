package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
       // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1 ; //중복 방지
        memo.setId(maxId);

        //DB 저장
        memoList.put(memo.getId(), memo);

        //Entity -> ResponseDto
        MemoResponseDto responseDto = new MemoResponseDto(memo);
        return responseDto;
    }

    @GetMapping
    public List<MemoResponseDto> getMemos() {
        //Map To List
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();
          return responseList;
    }
}

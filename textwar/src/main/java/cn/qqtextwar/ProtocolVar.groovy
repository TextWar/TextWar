package cn.qqtextwar

import groovy.transform.CompileStatic

/**
 * textwar规定的特殊字符和id范围
 */
@CompileStatic
interface ProtocolVar {

    Range<Integer> MOB_ID = 0x3EB..<0x7D0

    Range<Integer> DYNAMIC_BLOCK = 0x7D0..<0xBB8

    Range<Integer> NPC_ID = 0xBB8..<0x2710

    long PLAYER_MIN_ID = 0x2710

    String CROSS_LABEL = "*"

}

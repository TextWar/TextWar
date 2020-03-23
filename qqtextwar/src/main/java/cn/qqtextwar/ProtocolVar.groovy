package cn.qqtextwar

import groovy.transform.CompileStatic

/**
 * textwar规定的特殊字符和id范围
 */
@CompileStatic
interface ProtocolVar {

    Range<Integer> MOB_ID = 1000..<2000

    Range<Integer> DYNAMIC_BLOCK = 2000..<3000

    Range<Integer> NPC_ID = 3000..<10000

    int PLAYER_MIN_ID = 10000

    int WHITE_SPACE = 0

    String CROSS_LABEL = "*"

}

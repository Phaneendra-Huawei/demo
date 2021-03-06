/*
 * Copyright 2015-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.bgp;

import org.junit.Test;
import org.onosproject.bgpio.types.attr.BgpPrefixAttrOpaqueData;

import com.google.common.testing.EqualsTester;

/**
 * Test for prefix attribute opaque data Tlv.
 */
public class BgpPrefixAttrOpaqueDataTest {
    private final byte[] array = new byte[] {0x01, 0x02, 0x03, 0x04};
    private final byte[] array1 = new byte[] {0x01, 0x02, 0x03, 0x01};

    private final BgpPrefixAttrOpaqueData isisData = BgpPrefixAttrOpaqueData
            .of(array);
    private final BgpPrefixAttrOpaqueData sameAsIsisData = BgpPrefixAttrOpaqueData
            .of(array);
    private final BgpPrefixAttrOpaqueData isisDiff = BgpPrefixAttrOpaqueData
            .of(array1);

    @Test
    public void basics() {
        new EqualsTester().addEqualityGroup(isisData, sameAsIsisData)
                .addEqualityGroup(isisDiff).testEquals();
    }
}
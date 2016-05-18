/*
 *  Copyright 2016-present Open Networking Laboratory
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.onosproject.ui.impl.topo;

import com.google.common.collect.ImmutableSet;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.onlab.util.KryoNamespace;
import org.onosproject.store.serializers.KryoNamespaces;
import org.onosproject.store.service.ConsistentMap;
import org.onosproject.store.service.Serializer;
import org.onosproject.store.service.StorageService;
import org.onosproject.ui.UiTopoLayoutService;
import org.onosproject.ui.model.topo.UiTopoLayout;
import org.onosproject.ui.model.topo.UiTopoLayoutId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Manages the user interface topology layouts.
 * Note that these layouts are persisted and distributed across the cluster.
 */
@Component(immediate = true)
@Service
public class UiTopoLayoutManager implements UiTopoLayoutService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected StorageService storageService;

    private ConsistentMap<UiTopoLayoutId, UiTopoLayout> layouts;
    private Map<UiTopoLayoutId, UiTopoLayout> layoutMap;

    @Activate
    public void activate() {
        KryoNamespace.Builder kryoBuilder = new KryoNamespace.Builder()
                .register(KryoNamespaces.API)
                .register(UiTopoLayout.class);

        layouts = storageService.<UiTopoLayoutId, UiTopoLayout>consistentMapBuilder()
                .withSerializer(Serializer.using(kryoBuilder.build()))
                .withName("onos-topo-layouts")
                .withRelaxedReadConsistency()
                .build();
        layoutMap = layouts.asJavaMap();

        log.info("Started");
    }

    @Deactivate
    public void deactivate() {
        log.info("Stopped");
    }


    @Override
    public Set<UiTopoLayout> getLayouts() {
        return ImmutableSet.copyOf(layoutMap.values());
    }

    @Override
    public boolean addLayout(UiTopoLayout layout) {
        return layouts.put(layout.id(), layout) == null;
    }

    @Override
    public UiTopoLayout getLayout(UiTopoLayoutId layoutId) {
        return layoutMap.get(layoutId);
    }

    @Override
    public boolean removeLayout(UiTopoLayout layout) {
        return layouts.remove(layout.id()) != null;
    }

}

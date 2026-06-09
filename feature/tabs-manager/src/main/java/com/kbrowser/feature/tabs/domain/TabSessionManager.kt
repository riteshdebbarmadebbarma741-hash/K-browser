package com.kbrowser.feature.tabs.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

data class KTab(
    val id: String = UUID.randomUUID().toString(),
    val url: String,
    val title: String = "",
    val isIncognito: Boolean = false
)

@Singleton
class TabSessionManager @Inject constructor() {

    private val mutex = Mutex()

    private val _tabsState = MutableStateFlow<List<KTab>>(emptyList())
    val tabsState: StateFlow<List<KTab>> = _tabsState.asStateFlow()

    private val _activeTabId = MutableStateFlow<String?>(null)
    val activeTabId: StateFlow<String?> = _activeTabId.asStateFlow()

    suspend fun openNewTab(url: String, isIncognito: Boolean = false): KTab {
        return mutex.withLock {
            val newTab = KTab(url = url, isIncognito = isIncognito)
            _tabsState.update { currentTabs ->
                currentTabs + newTab
            }
            _activeTabId.value = newTab.id
            newTab
        }
    }

    suspend fun closeTab(tabId: String) {
        mutex.withLock {
            _tabsState.update { currentTabs ->
                currentTabs.filterNot { it.id == tabId }
            }
            if (_activeTabId.value == tabId) {
                _activeTabId.value = _tabsState.value.lastOrNull()?.id
            }
        }
    }

    suspend fun updateTabTitle(tabId: String, newTitle: String) {
        mutex.withLock {
            _tabsState.update { currentTabs ->
                currentTabs.map { tab ->
                    if (tab.id == tabId) tab.copy(title = newTitle) else tab
                }
            }
        }
    }
}

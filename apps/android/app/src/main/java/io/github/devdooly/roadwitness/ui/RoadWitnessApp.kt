package io.github.devdooly.roadwitness.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF162B3C)
private val Muted = Color(0xFF5F7280)
private val Teal = Color(0xFF087F79)
private val TealSoft = Color(0xFFE2F3EF)
private val Amber = Color(0xFF865815)
private val AmberSoft = Color(0xFFFFF2D9)
private val PageBackground = Color(0xFFF5F7F8)
private val CardBorder = Color(0xFFE3E9EC)
private val Navy = Color(0xFF142F43)

private enum class AppTab(val label: String, val icon: Glyph) {
    HOME("홈", Glyph.Home),
    DRIVING("주행", Glyph.Road),
    EVENTS("사건", Glyph.Document),
    SETTINGS("설정", Glyph.Settings),
}

private enum class Glyph {
    Home, Road, Document, Settings, Arrow, Camera, Check, Info, Storage,
}

/** The first app shell. Recording, detection and report preparation are not connected yet. */
@Composable
fun RoadWitnessApp() {
    var selectedTabName by rememberSaveable { mutableStateOf(AppTab.HOME.name) }
    val selectedTab = AppTab.entries.firstOrNull { it.name == selectedTabName } ?: AppTab.HOME
    val selectTab: (AppTab) -> Unit = { selectedTabName = it.name }

    BackHandler(enabled = selectedTab != AppTab.HOME) {
        selectedTabName = AppTab.HOME.name
    }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Teal,
            onPrimary = Color.White,
            primaryContainer = TealSoft,
            onPrimaryContainer = Teal,
            secondary = Ink,
            background = PageBackground,
            onBackground = Ink,
            surface = Color.White,
            onSurface = Ink,
            onSurfaceVariant = Muted,
            outline = CardBorder,
        ),
        typography = Typography(
            displaySmall = TextStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp, lineHeight = 42.sp),
            headlineMedium = TextStyle(fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 36.sp),
            titleLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 21.sp, lineHeight = 29.sp),
            titleMedium = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 24.sp),
            bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 25.sp),
            bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 22.sp),
            bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 19.sp),
            labelLarge = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 15.sp, lineHeight = 22.sp),
            labelMedium = TextStyle(fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 18.sp),
            labelSmall = TextStyle(fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp),
        ),
    ) {
        Scaffold(
            containerColor = PageBackground,
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = { BrandHeader() },
            bottomBar = {
                Surface(shadowElevation = 3.dp, color = Color.White) {
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 0.dp,
                        windowInsets = WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal,
                        ),
                    ) {
                        AppTab.entries.forEach { tab ->
                            NavigationBarItem(
                                selected = selectedTab == tab,
                                onClick = { selectTab(tab) },
                                icon = {
                                    AppGlyph(
                                        glyph = tab.icon,
                                        tint = if (selectedTab == tab) Teal else Muted,
                                    )
                                },
                                label = { Text(tab.label) },
                                modifier = Modifier.semantics {
                                    contentDescription = "${tab.label} 탭"
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Teal,
                                    selectedTextColor = Teal,
                                    indicatorColor = TealSoft,
                                    unselectedIconColor = Muted,
                                    unselectedTextColor = Muted,
                                ),
                            )
                        }
                    }
                }
            },
        ) { contentPadding ->
            // A separate scroll state for each screen prevents a previous tab's offset leaking.
            when (selectedTab) {
                AppTab.HOME -> HomeScreen(contentPadding, selectTab)
                AppTab.DRIVING -> DrivingScreen(contentPadding)
                AppTab.EVENTS -> EventsScreen(contentPadding, selectTab)
                AppTab.SETTINGS -> SettingsScreen(contentPadding)
            }
        }
    }
}

@Composable
private fun BrandHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PageBackground)
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal),
            )
            .padding(horizontal = 24.dp, vertical = 17.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(Teal, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center,
        ) {
            AppGlyph(Glyph.Road, Color.White, Modifier.size(22.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("RoadWitness", style = MaterialTheme.typography.titleMedium)
            Text("로드위트니스", style = MaterialTheme.typography.labelSmall, color = Muted)
        }
        StatusPill("0.1.0", Color.White, Muted)
    }
}

@Composable
private fun ScreenContent(
    contentPadding: PaddingValues,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp, top = 10.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        content = content,
    )
}

@Composable
private fun HomeScreen(contentPadding: PaddingValues, selectTab: (AppTab) -> Unit) {
    ScreenContent(contentPadding) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ScreenTitle("홈")
            Text(
                "주행의 기록을\n필요한 증거로.",
                style = MaterialTheme.typography.displaySmall,
                color = Ink,
            )
            Text(
                "위반 의심 장면을 검토하고 신고 자료를 준비하는 앱을 만들고 있습니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
            )
        }

        Surface(color = Navy, shape = RoundedCornerShape(26.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    AppGlyph(Glyph.Road, Color(0xFF7AD6C6), Modifier.size(28.dp))
                    Text(
                        "출발 전, 주행 화면부터",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f),
                    )
                }
                Text(
                    "촬영 기능은 준비 중입니다.\n먼저 주행 화면을 둘러보세요.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD0DFE7),
                )
                Button(
                    onClick = { selectTab(AppTab.DRIVING) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB5F1DD),
                        contentColor = Navy,
                    ),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 15.dp),
                ) {
                    Text("주행 화면 열기", modifier = Modifier.weight(1f))
                    AppGlyph(Glyph.Arrow, Navy)
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            SectionTitle("앞으로 제공할 흐름")
            WhiteCard {
                PlannedStep("01", "주행 영상 수집", "주행 영상을 기록하고 필요한 장면을 보존")
                PlannedStep("02", "의심 장면 검토", "사건 구간과 차량 정보를 사용자가 확인")
                PlannedStep("03", "신고 자료 준비", "증거 영상과 확인한 정보를 정리해 내보내기")
                StatusPill("모두 개발 예정", AmberSoft, Amber)
            }
        }

        WhiteCard {
            IconHeading(Glyph.Document, "사건 목록")
            Text(
                "저장된 사건을 모아 검토할 공간입니다. 현재 저장된 사건은 없습니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
            )
            OutlinedButton(
                onClick = { selectTab(AppTab.EVENTS) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CardBorder),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            ) {
                Text("사건 목록 보기")
            }
        }

        ImplementationNotice()
    }
}

@Composable
private fun DrivingScreen(contentPadding: PaddingValues) {
    ScreenContent(contentPadding) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ScreenTitle("주행")
            Text("출발 전에 주행 화면을 확인하세요.", style = MaterialTheme.typography.bodyLarge, color = Muted)
        }

        Surface(shape = RoundedCornerShape(26.dp), color = Navy) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                StatusPill("촬영 준비 중", Color(0xFF27485C), Color(0xFFDCEDF2))
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier.size(86.dp).background(Color(0xFF24465C), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    AppGlyph(Glyph.Camera, Color(0xFF9DE3D3), Modifier.size(42.dp), "카메라 기능 준비 중")
                }
                Text("촬영 화면 준비 중", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Text(
                    "현재는 카메라를 사용하거나\n주행 영상을 저장하지 않습니다.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFD0DFE7),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(6.dp))
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth().heightIn(min = 58.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color(0xFFE1E7EA),
                    disabledContentColor = Color(0xFF687C8A),
                ),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 16.dp),
            ) {
                AppGlyph(Glyph.Camera, Color(0xFF687C8A))
                Spacer(Modifier.width(10.dp))
                Text("촬영 시작 · 준비 중")
            }
            Text(
                "촬영과 실시간 분석은 아직 사용할 수 없습니다.",
                style = MaterialTheme.typography.bodySmall,
                color = Muted,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }

        WhiteCard {
            IconHeading(Glyph.Info, "주행 전 확인")
            GuidanceRow("휴대폰은 거치대에 고정하세요.")
            GuidanceRow("화면 조작과 사건 검토는 정차 후에 하세요.")
        }

        WhiteCard {
            SectionTitle("다음에 제공할 기능")
            Text(
                "주행 영상 촬영과 저장을 먼저 연결하고, 이후 위반 의심 장면을 찾는 기능을 추가할 예정입니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
            )
            StatusPill("개발 예정", AmberSoft, Amber)
        }
    }
}

@Composable
private fun EventsScreen(contentPadding: PaddingValues, selectTab: (AppTab) -> Unit) {
    ScreenContent(contentPadding) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ScreenTitle("사건", Modifier.weight(1f))
            StatusPill("0건", TealSoft, Teal)
        }

        WhiteCard {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp),
            ) {
                Box(
                    modifier = Modifier.size(92.dp).background(TealSoft, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    AppGlyph(Glyph.Document, Teal, Modifier.size(40.dp), "비어 있는 사건 목록")
                }
                Text(
                    "저장된 사건이 없습니다",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
                Text(
                    "위반 의심 장면을 저장하면\n이곳에서 내용을 검토할 수 있습니다.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Muted,
                    textAlign = TextAlign.Center,
                )
                StatusPill("사건 저장 기능 준비 중", AmberSoft, Amber)
                Button(
                    onClick = { selectTab(AppTab.DRIVING) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 15.dp),
                ) {
                    Text("주행 화면 열기")
                }
            }
        }

        WhiteCard {
            IconHeading(Glyph.Check, "내가 확인한 정보로 정리")
            Text(
                "번호판, 촬영 일시와 장소, 의심 장면을 직접 확인한 뒤 신고 자료를 준비하는 흐름을 개발할 예정입니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
            )
        }

        Text(
            "사건 생성, 영상 편집, 신고 자료 내보내기는 아직 구현되지 않았습니다.",
            style = MaterialTheme.typography.bodySmall,
            color = Muted,
        )
    }
}

@Composable
private fun SettingsScreen(contentPadding: PaddingValues) {
    ScreenContent(contentPadding) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ScreenTitle("설정")
            Text("앱의 현재 상태와 개발 방향을 확인하세요.", style = MaterialTheme.typography.bodyLarge, color = Muted)
        }

        WhiteCard {
            IconHeading(Glyph.Info, "앱 정보")
            Text("RoadWitness", style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text("초기 앱 버전", style = MaterialTheme.typography.bodyMedium, color = Muted, modifier = Modifier.weight(1f))
                StatusPill("0.1.0", PageBackground, Ink)
            }
        }

        WhiteCard {
            IconHeading(Glyph.Storage, "보관 방식")
            Text("휴대폰 안에서 보관하는 설계", style = MaterialTheme.typography.titleMedium)
            Text(
                "촬영 영상과 사건 정보를 기기에 보관하는 방향으로 개발합니다. 현재 앱은 영상을 수집하거나 저장하지 않습니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
            )
            StatusPill("로컬 보관 · 개발 예정", TealSoft, Teal)
        }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            SectionTitle("현재 사용할 수 있는 기능")
            WhiteCard {
                AvailableFeature("홈 · 주행 · 사건 · 설정 화면 이동")
                AvailableFeature("화면 회전 후 선택한 탭 복원")
                AvailableFeature("뒤로 가기로 홈 화면 복귀")
            }
        }

        WhiteCard {
            SectionTitle("준비 중인 기능")
            Text(
                "주행 영상 촬영 · 차량 및 번호판 분석\n사건 저장 · 증거 영상 편집\n신고 정보 요약 · 자료 내보내기",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
            )
            StatusPill("현재 사용할 수 없음", AmberSoft, Amber)
        }

        Text(
            "실제 신고 제출 기능은 연결되어 있지 않습니다.",
            style = MaterialTheme.typography.bodySmall,
            color = Muted,
        )
    }
}

@Composable
private fun ScreenTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        title,
        style = MaterialTheme.typography.headlineMedium,
        color = Ink,
        modifier = modifier.semantics { heading() },
    )
}

@Composable
private fun SectionTitle(title: String) {
    Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.semantics { heading() })
}

@Composable
private fun WhiteCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, CardBorder),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content,
        )
    }
}

@Composable
private fun IconHeading(glyph: Glyph, title: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        AppGlyph(glyph, Teal)
        Text(title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f).semantics { heading() })
    }
}

@Composable
private fun StatusPill(label: String, background: Color, foreground: Color) {
    Surface(color = background, shape = RoundedCornerShape(50)) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = foreground,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
        )
    }
}

@Composable
private fun PlannedStep(number: String, title: String, description: String) {
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(
            modifier = Modifier.size(36.dp).background(PageBackground, RoundedCornerShape(11.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(number, style = MaterialTheme.typography.labelMedium, color = Teal)
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(description, style = MaterialTheme.typography.bodyMedium, color = Muted)
        }
    }
}

@Composable
private fun GuidanceRow(text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
        Box(modifier = Modifier.padding(top = 8.dp).size(5.dp).background(Teal, CircleShape))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = Muted, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun AvailableFeature(text: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.Top) {
        AppGlyph(Glyph.Check, Teal, Modifier.size(20.dp).padding(top = 2.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ImplementationNotice() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
        AppGlyph(Glyph.Info, Muted, Modifier.size(18.dp).padding(top = 1.dp))
        Text(
            "현재는 기본 화면과 탭 이동만 제공됩니다. 촬영, AI 분석, 영상 편집과 신고 기능은 준비 중입니다.",
            style = MaterialTheme.typography.bodySmall,
            color = Muted,
            modifier = Modifier.weight(1f),
        )
    }
}

/** Small, original line icons keep the initial shell free of an icon-library dependency. */
@Composable
private fun AppGlyph(
    glyph: Glyph,
    tint: Color,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    val accessibleModifier = if (description == null) modifier else modifier.semantics {
        contentDescription = description
    }
    Canvas(modifier = accessibleModifier.size(24.dp)) {
        val unit = size.minDimension / 24f
        val left = (size.width - 24f * unit) / 2f
        val top = (size.height - 24f * unit) / 2f
        fun point(x: Float, y: Float) = Offset(left + x * unit, top + y * unit)
        val stroke = Stroke(width = 1.8f * unit, cap = StrokeCap.Round, join = StrokeJoin.Round)
        fun line(x1: Float, y1: Float, x2: Float, y2: Float) {
            drawLine(tint, point(x1, y1), point(x2, y2), strokeWidth = stroke.width, cap = StrokeCap.Round)
        }
        when (glyph) {
            Glyph.Home -> {
                val roof = Path().apply { moveTo(point(3f, 10f).x, point(3f, 10f).y); lineTo(point(12f, 3f).x, point(12f, 3f).y); lineTo(point(21f, 10f).x, point(21f, 10f).y) }
                drawPath(roof, tint, style = stroke)
                val house = Path().apply {
                    moveTo(point(5f, 9f).x, point(5f, 9f).y)
                    lineTo(point(5f, 21f).x, point(5f, 21f).y)
                    lineTo(point(10f, 21f).x, point(10f, 21f).y)
                    lineTo(point(10f, 14f).x, point(10f, 14f).y)
                    lineTo(point(14f, 14f).x, point(14f, 14f).y)
                    lineTo(point(14f, 21f).x, point(14f, 21f).y)
                    lineTo(point(19f, 21f).x, point(19f, 21f).y)
                    lineTo(point(19f, 9f).x, point(19f, 9f).y)
                }
                drawPath(house, tint, style = stroke)
            }
            Glyph.Road -> {
                line(8f, 3f, 4f, 21f)
                line(16f, 3f, 20f, 21f)
                line(12f, 3f, 12f, 6f)
                line(12f, 10f, 12f, 13f)
                line(12f, 17f, 12f, 21f)
            }
            Glyph.Document -> {
                drawRoundRect(tint, point(5f, 3f), Size(14f * unit, 18f * unit), CornerRadius(2f * unit), style = stroke)
                line(9f, 8f, 15f, 8f)
                line(9f, 12f, 15f, 12f)
                line(9f, 16f, 13f, 16f)
            }
            Glyph.Settings -> {
                drawCircle(tint, 6f * unit, point(12f, 12f), style = stroke)
                drawCircle(tint, 2.3f * unit, point(12f, 12f), style = stroke)
                line(12f, 2f, 12f, 5f)
                line(12f, 19f, 12f, 22f)
                line(2f, 12f, 5f, 12f)
                line(19f, 12f, 22f, 12f)
                line(4.9f, 4.9f, 7f, 7f)
                line(17f, 17f, 19.1f, 19.1f)
                line(4.9f, 19.1f, 7f, 17f)
                line(17f, 7f, 19.1f, 4.9f)
            }
            Glyph.Arrow -> {
                line(4f, 12f, 20f, 12f)
                line(14f, 6f, 20f, 12f)
                line(20f, 12f, 14f, 18f)
            }
            Glyph.Camera -> {
                val camera = Path().apply {
                    moveTo(point(3f, 7f).x, point(3f, 7f).y)
                    lineTo(point(8f, 7f).x, point(8f, 7f).y)
                    lineTo(point(10f, 4f).x, point(10f, 4f).y)
                    lineTo(point(14f, 4f).x, point(14f, 4f).y)
                    lineTo(point(16f, 7f).x, point(16f, 7f).y)
                    lineTo(point(21f, 7f).x, point(21f, 7f).y)
                    lineTo(point(21f, 20f).x, point(21f, 20f).y)
                    lineTo(point(3f, 20f).x, point(3f, 20f).y)
                    close()
                }
                drawPath(camera, tint, style = stroke)
                drawCircle(tint, 4f * unit, point(12f, 13f), style = stroke)
            }
            Glyph.Check -> {
                line(5f, 12f, 10f, 17f)
                line(10f, 17f, 19f, 7f)
            }
            Glyph.Info -> {
                drawCircle(tint, 9f * unit, point(12f, 12f), style = stroke)
                drawCircle(tint, 1f * unit, point(12f, 8f))
                line(12f, 12f, 12f, 17f)
            }
            Glyph.Storage -> {
                drawRoundRect(tint, point(6f, 2f), Size(12f * unit, 20f * unit), CornerRadius(2.5f * unit), style = stroke)
                line(10f, 5f, 14f, 5f)
                line(10f, 19f, 14f, 19f)
            }
        }
    }
}

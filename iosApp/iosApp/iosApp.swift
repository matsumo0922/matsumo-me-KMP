//
//  iosApp.swift
//  iosApp
//
//  Created by 松本大智 on 2025/02/20.
//

import SwiftUI

@main
struct iosAppApp: App {
    var body: some Scene {
        WindowGroup {
            GeometryReader { geo in
                ComposeViewController(
                    topSafeArea: Float(geo.safeAreaInsets.top),
                    bottomSafeArea: Float(geo.safeAreaInsets.bottom)
                )
                .edgesIgnoringSafeArea(.all)
                .onTapGesture {
                    UIApplication.shared.sendAction(
                        #selector(UIResponder.resignFirstResponder),
                        to: nil, from: nil, for: nil
                    )
                }
            }
        }
    }
}

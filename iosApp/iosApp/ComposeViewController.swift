import UIKit
import SwiftUI
import Frontend

struct ComposeViewController: UIViewControllerRepresentable {
    private let topSafeArea: Float
    private let bottomSafeArea: Float

    init(topSafeArea: Float, bottomSafeArea: Float) {
        self.topSafeArea = topSafeArea
        self.bottomSafeArea = bottomSafeArea
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

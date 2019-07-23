# Quicksave

A library for handling app settings in Android apps. *This library is in early design stages so its API may change drastically.*

 [ ![Download](https://api.bintray.com/packages/dellisd/Quicksave/quicksave/images/download.svg) ](https://bintray.com/dellisd/Quicksave/quicksave/_latestVersion)

## Getting Started

### Adding to Project
To add this library to your project, include the following

```groovy
repositories {
    maven { url "https://dl.bintray.com/dellisd/Quicksave" }
}

dependencies {
    implementation "io.github.dellisd.quicksave:quicksave:0.1.0-alpha01"
}
```

### Implementing
To implement your settings, extend the `SettingsProvider` interface and delegate the storage/access functions to one of the provided implementations of `SettingsProvider` (see below for the provided classes, or for how to implement your own).
Create properties for each of the settings you would like to save using the delegated `setting { }` DSL.

```kotlin
class MySettings(provider: SettingsProvider) : SettingsProvider by provider {
    val maxDownloadSize: Setting<Int> by setting { }  
}

val settings = MySettings(SharedPreferenceProvider(sharedPreferences))
```

### Using
To access and update your settings, simply access the values of your `Setting` properties through the instance of your `SettingsProvider`. The library will automatically access or update the saved value in the backing storage source.

```kotlin
// Access setting value
if (settings.maxDownloadSize.value > 1024) {
    // ...
}

// Update setting value
settings.maxDownloadSize.value = 2048 * 4
```

To listen for updates to setting values, add a listener to the `Setting` property.

```kotlin
settings.maxDownloadSize.addListener { newValue ->
    // Do something with the new value...
}
```

## `SettingsProvider`s
Quicksave comes with two SettingsProviders implementations that handle the storage of settings values: `SharedPreferenceProvider` which uses Android's `SharedPreferences`, and `MapProvider` which uses in-memory `Map`s to store key-value pairs.
Because these implementations handle the storage and access functions of the settings provider, you can delegate those responsibilities to one of these implementations from your custom settings implementation. All `Setting` properties declared in your settings implementation will automatically use the delegated functionality.

There may be cases where you want to use another storage method to persist your settings values and it is easy to create your own implementation of `SettingsProvider` to do this.
Simply extend the `SettingsProvider` interface and override the `get` and `set` methods.

#### get
The get method has a signature of `fun <T : Any> get(setting: Setting<T>): T`. It accepts the `Setting` property to get the value of, and returns the value of that setting.
Since `T` is erased at runtime, each `Setting<T>` object includes a `type: KClass<T>` property in order to determine the type of value being stored. This can be compared against in order to determine how to convert the stored value into a runtime value.

#### set
The set method has a signature of `fun <T : Any> set(setting: Setting<T>, value: T)`. It accepts the `Setting` property to set the value of, as well as the new value to set the setting to.
As with `get`, the `type` property of the setting can be used to determine how to store the setting's value.

## `Setting<T>`
The `Setting` class is used to represent each of the app's settings. Instances of this class can be obtained by using the `setting { }` delegated property which acts as a DSL to configure the setting. **Note:** The `setting { }` function can only be called within the body of a `SettingsProvider` class.

The following properties can be configured for a setting:

* `key` - The string key value that is used to identify this setting in storage. By default, it will use the name of the declared property.
* `default` - The default value of the setting if no previous value is present. If left unset (or null), both the provided `SettingsProvider`s will use `0` for numerical types, `false` for `Boolean`, and `null` for strings.
* `title` - The title of the setting to be displayed in the settings UI
* `caption` - The caption of the setting to be displayed in the settings UI
* `dependsOn` - Can be set to a `Setting<Boolean>` that this setting depends on for UI purposes. The setting being configured will only be enabled if the other setting has a value of true.

These properties can be configured like this:
```kotlin
// All default values
val toggle: Setting<Boolean> by setting { }

// All overridden values
val volume: Setting<Int> by setting {
    key = "volume"
    default = 100
    title = "Audio Volume"
    caption = "How loud to make the music"
    dependsOn(toggle)
}
```

## Settings Screen (early design)
Quicksave provides a simple way to generate a settings screen from a set of settings. Simply add a `SettingsLayout` to your layout and then call `inflateSettings(vararg settings: Setting<*>)` with the list of settings you want to display.
The library will add views to the `SettingsLayout` that allow the settings to be updated by the user.

```kotlin
val settingsLayout = findViewById<SettingsLayout>(R.id.settings_layout)

// settings from above
settingsLayout.inflateSettings(toggle, volume)
```
using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Device.Locker.DeviceLocker
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class DeviceLockerModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="DeviceLockerModule"/>.
        /// </summary>
        internal DeviceLockerModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "DeviceLocker";
            }
        }
    }
}
